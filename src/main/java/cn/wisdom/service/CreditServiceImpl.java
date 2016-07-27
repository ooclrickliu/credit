package cn.wisdom.service;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.chanjar.weixin.common.exception.WxErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.wisdom.api.response.AccountProfile;
import cn.wisdom.common.log.Logger;
import cn.wisdom.common.log.LoggerFactory;
import cn.wisdom.common.utils.DateTimeUtils;
import cn.wisdom.common.utils.StringUtils;
import cn.wisdom.dao.CreditApplyDao;
import cn.wisdom.dao.CreditPayDao;
import cn.wisdom.dao.constant.ApplyState;
import cn.wisdom.dao.vo.AppProperty;
import cn.wisdom.dao.vo.CreditApply;
import cn.wisdom.dao.vo.CreditPayRecord;
import cn.wisdom.dao.vo.DateRange;
import cn.wisdom.dao.vo.User;
import cn.wisdom.service.context.SessionContext;
import cn.wisdom.service.exception.ServiceErrorCode;
import cn.wisdom.service.exception.ServiceException;
import cn.wisdom.service.wx.MessageNotifier;
import cn.wisdom.service.wx.WXService;

@Service
public class CreditServiceImpl implements CreditService {

	@Autowired
	private CreditCalculator creditCalculator;

	@Autowired
	private CreditApplyDao creditApplyDao;

	@Autowired
	private CreditPayDao creditPayDao;

	@Autowired
	private WXService wxService;

	@Autowired
	private MessageNotifier messageNotifier;

	@Autowired
	private AppProperty appProperty;

	private static final Logger logger = LoggerFactory
			.getLogger(CreditServiceImpl.class.getName());

	@Override
	public CreditApply applyCreditStep1(CreditApply creditApply)
			throws ServiceException {

		// 0. check available credit line
		checkAvailableCreditLine(creditApply);

		// 1. calculate fee
		creditCalculator.calculateFee(creditApply);

		// 2. state
		if (isCreditApplyReady(creditApply)) {
			creditApply.setApplyState(ApplyState.Approving);
		} else {
			creditApply.setApplyState(ApplyState.Applying);
		}
		creditApply.setApplyTime(DateTimeUtils.getCurrentTimestamp());

		creditApplyDao.saveCreditApply(creditApply);

		return creditApply;
	}

	private void checkAvailableCreditLine(CreditApply creditApply)
			throws ServiceException {
		AccountProfile accountProfile = this.getAccountProfile(creditApply
				.getUserId());
		if (accountProfile.getAvailableCreditLine() <= 0 || 
				creditApply.getAmount() > accountProfile.getAvailableCreditLine()) {
			throw new ServiceException(
					ServiceErrorCode.NO_AVAILABLE_CREDIT_LINE,
					"No available credit line.");
		}
	}

	private boolean isCreditApplyReady(CreditApply creditApply) {

		return creditApply.getAmount() > 0 && creditApply.getMonth() > 0
				&& StringUtils.isNotBlank(creditApply.getCommissionImgUrl());
	}

	@Override
	public void applyCreditStep2(long applyId, String commissionImgUrl) {

		CreditApply creditApply = creditApplyDao.getApply(applyId);

		try {
			File commissionImg = wxService.getWxMpService().mediaDownload(
					commissionImgUrl);
			creditApply.setCommissionImgUrl(commissionImg.getAbsolutePath());

			if (isCreditApplyReady(creditApply)) {
				creditApply.setApplyState(ApplyState.Approving);

				messageNotifier.notifyBossNewApply(creditApply);
			} else {
				creditApply.setApplyState(ApplyState.Applying);
			}

			creditApplyDao.updateCommissionInfo(creditApply);
		} catch (WxErrorException e) {
			logger.error("failed to upload commissionImg", e);
			creditApply.setCommissionImgUrl(commissionImgUrl);
		}
	}

	@Override
	public void approve(long applyId) throws ServiceException {

		CreditApply apply = creditApplyDao.getApply(applyId);

		checkAvailableCreditLine(apply);

		Date today = new Date();
		Timestamp dueTime = DateTimeUtils.toTimestamp(DateTimeUtils.addMonths(
				today, apply.getMonth()));
		apply.setDueTime(dueTime);
		apply.setApplyState(ApplyState.Approved);

		creditApplyDao.updateApplyApproveInfo(apply);

		try {
			messageNotifier.notifyUserApplyApproved(apply);
		} catch (WxErrorException e) {
			logger.error("failed to notify user apply approved.", e);
		}
	}

	@Override
	public void reject(long applyId, String note) {

		creditApplyDao.updateApplyApproveInfo(applyId, note,
				ApplyState.ApproveFailed);

	}

	@Override
	public void returnCredit(long applyId, String returnCreditImgUrl) {
		try {
			File returnCreditImg = wxService.getWxMpService().mediaDownload(
					returnCreditImgUrl);

			// get last pay record
			CreditPayRecord lastPayRecord = creditPayDao
					.getLastPayRecord(applyId);

			// create a new pay record
			CreditPayRecord newPayRecord = new CreditPayRecord();
			if (lastPayRecord == null) {
				CreditApply creditApply = creditApplyDao.getApply(applyId);

				newPayRecord.setApplyId(applyId);
				newPayRecord.setCreditBase(creditApply.getAmount());
				newPayRecord.setRemainBase(creditApply.getAmount());
				newPayRecord.setReturnState(ApplyState.Approving);
				newPayRecord.setReturnTime(DateTimeUtils.getCurrentTimestamp());
				newPayRecord.setPayImgUrl(returnCreditImg.getAbsolutePath());

				float interest = creditCalculator.calculateInterest(
						creditApply.getAmount(),
						creditApply.getEffectiveTime(),
						appProperty.creditRatePerDay);
				newPayRecord.setInterest(interest);
			} else {
				newPayRecord.setApplyId(applyId);
				newPayRecord.setCreditBase(lastPayRecord.getRemainBase());
				newPayRecord.setRemainBase(lastPayRecord.getRemainBase());
				newPayRecord.setReturnState(ApplyState.Approving);
				newPayRecord.setReturnTime(DateTimeUtils.getCurrentTimestamp());
				newPayRecord.setPayImgUrl(returnCreditImg.getAbsolutePath());

				float interest = creditCalculator.calculateInterest(
						lastPayRecord.getRemainBase(),
						lastPayRecord.getReturnTime(),
						appProperty.creditRatePerDay);
				newPayRecord.setInterest(interest);

			}
			creditPayDao.save(newPayRecord);
		} catch (WxErrorException e) {
			logger.error("failed to upload returnCreditImg", e);
		}
	}

	@Override
	@Transactional
	public void confirmReturn(long payRecordId, float returnAmount) {

		// update credit pay record
		CreditPayRecord payRecord = creditPayDao.getPayRecord(payRecordId);
		payRecord.setReturnedAmount(returnAmount);

		float remainBase = payRecord.getRemainBase()
				- (returnAmount - payRecord.getInterest());
		if (remainBase < 0) {
			remainBase = 0;
		}
		payRecord.setRemainBase(remainBase);
		payRecord.setReturnState(ApplyState.Approved);

		creditPayDao.updatePayRecordReturnInfo(payRecord);

		// update credit apply
		CreditApply apply = creditApplyDao.getApply(payRecord.getApplyId());
		apply.setReturnedBase(apply.getReturnedBase()
				+ (returnAmount - payRecord.getInterest()));
		if (apply.getAmount() <= apply.getReturnedBase()) {
			apply.setApplyState(ApplyState.ReturnDone);
		}
		creditApplyDao.updateReturnInfo(apply);
		
		try {
			messageNotifier.notifyReturnSuccess(apply, payRecord);
		} catch (WxErrorException e) {
			logger.error("failed to notify user return success!", e);
		}
	}

	@Override
	public void returnFail(long payRecordId) {

		creditPayDao.updatePayRecordState(payRecordId, ApplyState.Approving,
				ApplyState.ApproveFailed);
		
		CreditPayRecord payRecord = creditPayDao.getPayRecord(payRecordId);
		CreditApply apply = creditApplyDao.getApply(payRecord.getApplyId());
		
		try {
			messageNotifier.notifyReturnFailed(apply, payRecord);
		} catch (WxErrorException e) {
			logger.error("failed to notify user return fail!", e);
		}
	}

	@Override
	public List<CreditApply> getApplyList(long userId, boolean asc) {

		return getApplyList(userId, null, null, asc);
	}

	@Override
	public List<CreditApply> getApplyList(long userId,
			List<ApplyState> applyStates, Date toDate, boolean asc) {

		List<CreditApply> applyList = creditApplyDao.getApplyList(userId,
				applyStates, toDate, asc);

		for (CreditApply creditApply : applyList) {
			if (creditApply.getApplyState() == ApplyState.Approved
					|| creditApply.getApplyState() == ApplyState.Overdue) {
				float interest = creditCalculator
						.calculateInterest(creditApply.getAmount()
								- creditApply.getReturnedBase(),
								creditApply.getEffectiveTime(),
								appProperty.creditRatePerDay);
				creditApply.setInterest(interest);
			}
		}

		return applyList;
	}

	@Override
	public AccountProfile getAccountProfile(long userId) {

		AccountProfile accountProfile = new AccountProfile();

		User user = SessionContext.getCurrentUser();

		//
		accountProfile.setRatePerDay(appProperty.creditRatePerDay);
		accountProfile.setCommissionRate(appProperty.creditCommissionRate);
		accountProfile.setTotalCreditLine(user.getCreditLine());

		//
		List<CreditApply> topayApplyList = this.getTopayApplyList(user.getId(),
				DateRange.All);

		Date today = new Date();
		accountProfile.setPending7DAmount(this.getPendingAmount(topayApplyList,
				DateTimeUtils.addDays(today, 7)));
		accountProfile.setPending30DAmount(this.getPendingAmount(
				topayApplyList, DateTimeUtils.addDays(today, 30)));
		accountProfile.setPendingTotalAmount(this.getPendingAmount(
				topayApplyList, DateTimeUtils.addYears(today, 10)));

		//
		accountProfile.setAvailableCreditLine(accountProfile
				.getTotalCreditLine() - accountProfile.getPendingTotalAmount());

		return accountProfile;
	}

	private float getPendingAmount(List<CreditApply> topayApplyList,
			Date dueDate) {
		float pendingAmount = 0f;
		for (CreditApply creditApply : topayApplyList) {
			if (creditApply.getDueTime().before(dueDate)) {
				pendingAmount += (creditApply.getAmount() - creditApply
						.getReturnedBase());
			}
		}

		return pendingAmount;
	}

	@Override
	public List<CreditApply> getOverdueApplyList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateOverdueApplyState() {
		creditApplyDao.updateOverdueState();
	}

	@Override
	public List<CreditApply> getTopayApplyList(long userId, DateRange dateRange) {
		List<ApplyState> states = new ArrayList<ApplyState>();
		states.add(ApplyState.Approved);
		states.add(ApplyState.Overdue);
		List<CreditApply> applyList = this.getApplyList(userId, states,
				dateRange.exactTime(), true);

		return applyList;
	}

	@Override
	public void deleteApply(long applyId) {
		
		creditApplyDao.deleteApply(applyId);
	}

	@Override
	public List<CreditPayRecord> getApplyPayRecords(String applyId) {
		
		return creditPayDao.getApplyPayRecords(applyId);
	}
	
	@Override
	public List<CreditPayRecord> getApplyPayRecords(ApplyState applyState) {
		return creditPayDao.getApplyPayRecords(applyState);
	}
	
	@Override
	public CreditApply getPayRecordApply(long payRecordId) {
		return creditPayDao.getPayRecordApply(payRecordId);
	}
}

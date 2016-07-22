package cn.wisdom.service;

import java.io.File;

import me.chanjar.weixin.common.exception.WxErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	private AppProperty appProperty;

	private static final Logger logger = LoggerFactory
			.getLogger(CreditServiceImpl.class.getName());

	@Override
	public CreditApply applyCreditStep1(CreditApply creditApply) {

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

	private boolean isCreditApplyReady(CreditApply creditApply) {

		return creditApply.getAmount() > 0 && creditApply.getMonth() > 0
				&& StringUtils.isNotBlank(creditApply.getCommissionImgUrl());
	}

	@Override
	public void applyCreditStep2(CreditApply creditApply) {

		try {
			File commissionImg = wxService.getWxMpService().mediaDownload(
					creditApply.getCommissionImgUrl());
			creditApply.setCommissionImgUrl(commissionImg.getAbsolutePath());
		} catch (WxErrorException e) {
			logger.error("failed to upload commissionImg", e);
		}

		if (isCreditApplyReady(creditApply)) {
			creditApply.setApplyState(ApplyState.Approving);
		} else {
			creditApply.setApplyState(ApplyState.Applying);
		}

		creditApplyDao.updateCommissionInfo(creditApply);
	}

	@Override
	public void approve(long applyId) {

		creditApplyDao.updateApplyState(applyId, ApplyState.Approving,
				ApplyState.Approved);
	}

	@Override
	public void reject(long applyId) {

		creditApplyDao.updateApplyState(applyId, ApplyState.Approving,
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
	public void confirmReturn(long payRecordId, float returnAmount) {
		
		CreditPayRecord payRecord = creditPayDao.getPayRecord(payRecordId);
		payRecord.setReturnedAmount(returnAmount);
		
		float remainBase = payRecord.getRemainBase() - (returnAmount - payRecord.getInterest());
		if (remainBase < 0) {
			remainBase = 0;
		}
		payRecord.setRemainBase(remainBase);
		payRecord.setReturnState(ApplyState.Approved);
		
		creditPayDao.updatePayRecord(payRecord);
	}

	@Override
	public void returnFail(long payRecordId) {
		
		creditPayDao.updatePayRecordState(payRecordId, ApplyState.Approving, ApplyState.ApproveFailed);
	}

}

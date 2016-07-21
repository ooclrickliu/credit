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
import cn.wisdom.dao.constant.ApplyState;
import cn.wisdom.dao.vo.CreditApply;
import cn.wisdom.service.wx.WXService;

@Service
public class CreditServiceImpl implements CreditService {
	
	@Autowired
	private CreditCalculator creditCalculator;
	
	@Autowired
	private CreditApplyDao creditApplyDao;
	
	@Autowired
	private WXService wxService;
	
    private static final Logger logger = LoggerFactory.getLogger(CreditServiceImpl.class.getName());
	
	@Override
	public CreditApply applyCreditStep1(CreditApply creditApply) {
		
		// 1. calculate fee
		creditCalculator.calculateFee(creditApply);
		
		// 2. state
		if (isCreditApplyReady(creditApply)) {
			creditApply.setApplyState(ApplyState.Approving);
		}
		else {
			creditApply.setApplyState(ApplyState.Applying);
		}
		creditApply.setApplyTime(DateTimeUtils.getCurrentTimestamp());
		
		creditApplyDao.saveCreditApply(creditApply);
		
		return creditApply;
	}

	private boolean isCreditApplyReady(CreditApply creditApply) {
		
		return creditApply.getAmount() > 0 && creditApply.getMonth() > 0 &&
				StringUtils.isNotBlank(creditApply.getCommissionImgUrl());
	}

	@Override
	public void applyCreditStep2(CreditApply creditApply) {

		try {
			File commissionImg = wxService.getWxMpService().mediaDownload(creditApply.getCommissionImgUrl());
			creditApply.setCommissionImgUrl(commissionImg.getAbsolutePath());
		} catch (WxErrorException e) {
			logger.error("failed to upload commissionImg", e);
		}
		
		if (isCreditApplyReady(creditApply)) {
			creditApply.setApplyState(ApplyState.Approving);
		}
		else {
			creditApply.setApplyState(ApplyState.Applying);
		}
		
		creditApplyDao.updateCommissionInfo(creditApply);
	}

}

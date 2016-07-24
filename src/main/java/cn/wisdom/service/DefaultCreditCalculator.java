package cn.wisdom.service;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.wisdom.common.utils.DateTimeUtils;
import cn.wisdom.common.utils.NumberUtils;
import cn.wisdom.dao.vo.AppProperty;
import cn.wisdom.dao.vo.CreditApply;

@Service
public class DefaultCreditCalculator implements CreditCalculator {

	@Autowired
	private AppProperty appProperty;
	
	@Override
	public void calculateFee(CreditApply creditApply) {
		// 1. interest
		Date today = new Date();
		Date dueDate = DateTimeUtils.addMonths(today, creditApply.getMonth());
		int days = DateTimeUtils.dateDiff(today, dueDate);
		
		float interest = NumberUtils.formatFloat(creditApply.getAmount() * days * appProperty.creditRatePerDay);
		creditApply.setInterest(interest);
		
		// 2. commission
		float commission = NumberUtils.formatFloat(creditApply.getAmount() * appProperty.creditCommissionRate);
		creditApply.setCommission(commission);
	}

	@Override
	public float calculateInterest(float amount, Timestamp effectiveTime,
			float creditRatePerDay) {
		
		int days = DateTimeUtils.dateDiff(effectiveTime, DateTimeUtils.getCurrentTimestamp());
		
		return NumberUtils.formatFloat(amount * creditRatePerDay * days);
	}

}

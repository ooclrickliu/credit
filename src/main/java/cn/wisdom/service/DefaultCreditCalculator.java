package cn.wisdom.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.wisdom.common.utils.DateTimeUtils;
import cn.wisdom.dao.vo.AppProperty;
import cn.wisdom.dao.vo.CreditApply;

@Service
public class DefaultCreditCalculator implements CreditCalculator {

	@Autowired
	private AppProperty appProperty;
	
	@Override
	public void calculateFee(CreditApply creditApply) {
		// 1. interest
		float rate = appProperty.creditRatePerDay;
		
		Date today = new Date();
		Date dueDate = DateTimeUtils.addMonths(today, creditApply.getMonth());
		int days = DateTimeUtils.dateDiff(today, dueDate);
		
		float interest = days * rate;
		creditApply.setInterest(interest);
		
		// 2. commission
		
		
	}

}

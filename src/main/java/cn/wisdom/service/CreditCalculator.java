package cn.wisdom.service;

import java.sql.Timestamp;

import cn.wisdom.dao.vo.CreditApply;
import cn.wisdom.dao.vo.User;

public interface CreditCalculator {

	/**
	 * Calculate interest and commission.
	 * 
	 * @param creditApply
	 */
	void calculateFee(CreditApply creditApply);

	/**
	 * Calculate interest.
	 * 
	 * @param amount
	 * @param effectiveTime
	 * @param creditRatePerDay
	 * @return
	 */
	float calculateInterest(float amount, Timestamp effectiveTime,
			float creditRatePerDay);
	
	
	float calculateUserCreditLine(User user);
}

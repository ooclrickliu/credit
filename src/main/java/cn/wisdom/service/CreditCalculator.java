package cn.wisdom.service;

import java.sql.Timestamp;

import cn.wisdom.dao.vo.CreditApply;

public interface CreditCalculator {

	/**
	 * Calculate interest and commission.
	 * 
	 * @param creditApply
	 */
	void calculateFee(CreditApply creditApply);

	float calculateInterest(float amount, Timestamp effectiveTime,
			float creditRatePerDay);
}

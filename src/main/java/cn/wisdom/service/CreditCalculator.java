package cn.wisdom.service;

import cn.wisdom.dao.vo.CreditApply;

public interface CreditCalculator {

	/**
	 * Calculate interest and commission.
	 * 
	 * @param creditApply
	 */
	void calculateFee(CreditApply creditApply);
}

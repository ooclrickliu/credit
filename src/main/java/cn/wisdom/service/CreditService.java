package cn.wisdom.service;

import cn.wisdom.dao.vo.CreditApply;

public interface CreditService {

	CreditApply applyCreditStep1(CreditApply creditApply);

	void applyCreditStep2(CreditApply creditApply);

}

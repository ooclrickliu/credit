package cn.wisdom.service;

import cn.wisdom.dao.vo.CreditApply;

public interface CreditService {

	CreditApply applyCreditStep1(CreditApply creditApply);

	void applyCreditStep2(CreditApply creditApply);

	void approve(long applyId);

	void reject(long applyId);

	void returnCredit(long applyId, String returnCreditImgUrl);

	void confirmReturn(long payRecordId, float returnAmount);

	void returnFail(long payRecordId);

}

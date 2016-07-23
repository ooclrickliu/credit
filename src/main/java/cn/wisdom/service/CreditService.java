package cn.wisdom.service;

import java.util.List;

import cn.wisdom.api.response.AccountProfile;
import cn.wisdom.dao.vo.CreditApply;

public interface CreditService {

	CreditApply applyCreditStep1(CreditApply creditApply);

	void applyCreditStep2(CreditApply creditApply);

	void approve(long applyId);

	void reject(long applyId, String note);

	void returnCredit(long applyId, String returnCreditImgUrl);

	void confirmReturn(long payRecordId, float returnAmount);

	void returnFail(long payRecordId);

	List<CreditApply> getApplyList(long userId);

	AccountProfile getAccountProfile(long userId);

}

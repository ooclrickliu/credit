package cn.wisdom.service;

import java.util.List;

import cn.wisdom.api.response.AccountProfile;
import cn.wisdom.dao.constant.ApplyState;
import cn.wisdom.dao.vo.CreditApply;
import cn.wisdom.service.exception.ServiceException;

public interface CreditService {

	CreditApply applyCreditStep1(CreditApply creditApply) throws ServiceException;

	void applyCreditStep2(long applyId, String commissionImgUrl);

	void approve(long applyId);

	void reject(long applyId, String note);

	void returnCredit(long applyId, String returnCreditImgUrl);

	void confirmReturn(long payRecordId, float returnAmount);

	void returnFail(long payRecordId);

	List<CreditApply> getApplyList(long userId, List<ApplyState> states);
	
	List<CreditApply> getTopayApplyList(long userId);

	AccountProfile getAccountProfile(long userId);

	List<CreditApply> getOverdueApplyList();

	void updateOverdueApplyState();

}

package cn.wisdom.dao;

import java.util.Date;
import java.util.List;

import cn.wisdom.dao.constant.ApplyState;
import cn.wisdom.dao.vo.CreditApply;

public interface CreditApplyDao {

	void saveCreditApply(CreditApply creditApply);

	void updateCommissionInfo(CreditApply creditApply);

	void updateApplyApproveInfo(long applyId, String note,
			ApplyState toState);

	void updateApplyApproveInfo(CreditApply apply);

	CreditApply getApply(long applyId);

	List<CreditApply> getApplyList(long userId, List<ApplyState> applyStates, Date toDate, boolean asc);

	void updateReturnInfo(CreditApply apply);

	void updateOverdueState();

	void deleteApply(long applyId);
}

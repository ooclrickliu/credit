package cn.wisdom.dao;

import java.util.List;

import cn.wisdom.dao.constant.ApplyState;
import cn.wisdom.dao.vo.CreditApply;

public interface CreditApplyDao {

	void saveCreditApply(CreditApply creditApply);

	void updateCommissionInfo(CreditApply creditApply);

	void updateApplyState(long applyId, ApplyState fromState,
			ApplyState toState);

	CreditApply getApply(long applyId);

	List<CreditApply> getApplyList(long userId);
}

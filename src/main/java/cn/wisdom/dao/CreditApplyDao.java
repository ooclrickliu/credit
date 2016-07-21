package cn.wisdom.dao;

import cn.wisdom.dao.vo.CreditApply;

public interface CreditApplyDao {

	void saveCreditApply(CreditApply creditApply);

	void updateCommissionInfo(CreditApply creditApply);
}

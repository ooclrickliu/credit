package cn.wisdom.dao;

import cn.wisdom.dao.constant.ApplyState;
import cn.wisdom.dao.vo.CreditPayRecord;

public interface CreditPayDao {
	
	/**
	 * 查询上次还款记录
	 * 
	 * @param applyId
	 * @return
	 */
	CreditPayRecord getLastPayRecord(long applyId);

	void save(CreditPayRecord newPayRecord);

	CreditPayRecord getPayRecord(long payRecordId);

	void updatePayRecordState(long payRecordId, ApplyState approving,
			ApplyState approvefailed);

	void updatePayRecord(CreditPayRecord payRecord);
}

package cn.wisdom.dao;

import org.springframework.stereotype.Repository;

import cn.wisdom.dao.constant.ApplyState;
import cn.wisdom.dao.vo.CreditPayRecord;

@Repository
public class CreditPayDaoImpl implements CreditPayDao {

	@Override
	public CreditPayRecord getLastPayRecord(long applyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(CreditPayRecord newPayRecord) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CreditPayRecord getPayRecord(long payRecordId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updatePayRecordState(long payRecordId, ApplyState approving,
			ApplyState approvefailed) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updatePayRecord(CreditPayRecord payRecord) {
		// TODO Auto-generated method stub
		
	}

}

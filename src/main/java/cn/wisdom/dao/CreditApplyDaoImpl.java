package cn.wisdom.dao;

import org.springframework.stereotype.Repository;

import cn.wisdom.dao.constant.ApplyState;
import cn.wisdom.dao.vo.CreditApply;

@Repository
public class CreditApplyDaoImpl implements CreditApplyDao {

	@Override
	public void saveCreditApply(CreditApply creditApply) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCommissionInfo(CreditApply creditApply) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateApplyState(long applyId, ApplyState fromState,
			ApplyState toState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CreditApply getApply(long applyId) {
		// TODO Auto-generated method stub
		return null;
	}

}

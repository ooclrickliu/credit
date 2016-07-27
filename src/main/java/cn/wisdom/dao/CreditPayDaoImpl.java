package cn.wisdom.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.wisdom.dao.constant.ApplyState;
import cn.wisdom.dao.mapper.DaoRowMapper;
import cn.wisdom.dao.vo.CreditApply;
import cn.wisdom.dao.vo.CreditPayRecord;

@Repository
public class CreditPayDaoImpl implements CreditPayDao {

	@Autowired
	private DaoHelper daoHelper;

	private static final String SQL_SAVE_PAY_RECORD = "insert ignore into credit_pay_record(apply_id, credit_base, remain_base, return_state, return_time, pay_img_url, credit_interest, update_time) values (?, ?, ?, ?, ?, ?, ?, current_timestamp)";

	private static final String SQL_UPDATE_PAY_STATE = "update credit_pay_record set return_state = ?, update_time = current_timestamp where id = ? and return_state = ?";
	
	private static final String SQL_UPDATE_PAY_INFO = "update credit_pay_record set returned_amount = ?, remain_base = ?, return_state = ?, update_time = current_timestamp where id = ? and return_state = ?";

	private static final String SQL_GET_CREDIT_PAY_PREFIX = "select * from credit_pay_record ";

	private static final String SQL_GET_LAST_PAY_OF_APPLY = SQL_GET_CREDIT_PAY_PREFIX
			+ "where apply_id = ? order by id desc limit 1";
	
	private static final String SQL_GET_All_PAY_OF_APPLY = SQL_GET_CREDIT_PAY_PREFIX
			+ "where apply_id = ? order by id desc";

	private static final String SQL_GET_BY_ID = SQL_GET_CREDIT_PAY_PREFIX
			+ "where id = ?";
	
	private static final String SQL_GET_BY_STATE = SQL_GET_CREDIT_PAY_PREFIX
			+ "where return_state = ?";
	
	private static final String SQL_GET_APPLY_BY_PAY = "select a.* from credit_pay_record b "
			+ "inner join credit_apply a on b.apply_id = a.id where b.id = ?";

	private static final DaoRowMapper<CreditPayRecord> creditPayMapper = new DaoRowMapper<CreditPayRecord>(
			CreditPayRecord.class);
	
	private static final DaoRowMapper<CreditApply> creditApplyMapper = new DaoRowMapper<CreditApply>(
			CreditApply.class);

	@Override
	public CreditPayRecord getLastPayRecord(long applyId) {

		String errMsg = "Failed to get credit pay record of apply: " + applyId;
		CreditPayRecord creditPayRecord = daoHelper.queryForObject(
				SQL_GET_LAST_PAY_OF_APPLY, creditPayMapper, errMsg, applyId);

		return creditPayRecord;
	}

	@Override
	public void save(CreditPayRecord newPayRecord) {
		String errMsg = "Failed to save new pay record, applyId: "
				+ newPayRecord.getApplyId();
		daoHelper.save(SQL_SAVE_PAY_RECORD, errMsg, false, newPayRecord
				.getApplyId(), newPayRecord.getCreditBase(), newPayRecord
				.getRemainBase(), newPayRecord.getReturnState().toString(),
				newPayRecord.getReturnTime(), newPayRecord.getPayImgUrl(),
				newPayRecord.getInterest());
	}

	@Override
	public CreditPayRecord getPayRecord(long payRecordId) {
		String errMsg = "Failed to get credit pay record by id: " + payRecordId;
		CreditPayRecord creditPayRecord = daoHelper.queryForObject(
				SQL_GET_BY_ID, creditPayMapper, errMsg, payRecordId);

		return creditPayRecord;
	}

	@Override
	public void updatePayRecordState(long payRecordId, ApplyState fromState,
			ApplyState toState) {

		String errMsg = "Failed to update credit pay record state, id: "
				+ payRecordId;
		daoHelper.update(SQL_UPDATE_PAY_STATE, errMsg, toState.toString(), payRecordId,
				fromState.toString());
	}

	@Override
	public void updatePayRecordReturnInfo(CreditPayRecord payRecord) {

		String errMsg = "Failed to update credit pay record state, id: "
				+ payRecord.getId();
		daoHelper.update(SQL_UPDATE_PAY_INFO, errMsg, payRecord.getReturnedAmount(), payRecord.getRemainBase(),
				payRecord.getReturnState().toString(), payRecord.getId(),
				ApplyState.Approving.toString());
	}

	@Override
	public List<CreditPayRecord> getApplyPayRecords(String applyId) {
		String errMsg = "Failed to get credit pay record by apply id: " + applyId;
		List<CreditPayRecord> creditPayRecords = daoHelper.queryForList(
				SQL_GET_All_PAY_OF_APPLY, creditPayMapper, errMsg, applyId);
		
		return creditPayRecords;
	}
	
	@Override
	public List<CreditPayRecord> getApplyPayRecords(ApplyState applyState) {
		String errMsg = "Failed to get credit pay record by state: " + applyState;
		List<CreditPayRecord> creditPayRecords = daoHelper.queryForList(
				SQL_GET_BY_STATE, creditPayMapper, errMsg, applyState.toString());
		
		return creditPayRecords;
	}
	
	@Override
	public CreditApply getPayRecordApply(long payRecordId) {
		String errMsg = "Failed to get credit apply of pay record by state: " + payRecordId;
		CreditApply creditApply = daoHelper.queryForObject(
				SQL_GET_APPLY_BY_PAY, creditApplyMapper, errMsg, payRecordId);
		
		return creditApply;
	}
}

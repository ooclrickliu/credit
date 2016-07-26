package cn.wisdom.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.wisdom.common.utils.CollectionUtils;
import cn.wisdom.common.utils.StringUtils;
import cn.wisdom.dao.constant.ApplyState;
import cn.wisdom.dao.mapper.DaoRowMapper;
import cn.wisdom.dao.vo.CreditApply;

@Repository
public class CreditApplyDaoImpl implements CreditApplyDao {

	@Autowired
	private DaoHelper daoHelper;

	private static final String SQL_SAVE_CREDIT_APPLY = "insert ignore into credit_apply(user_id, amount, month, interest, commission, apply_state, apply_time, update_time) values (?, ?, ?, ?, ?, ?, ?, current_timestamp)";

	private static final String SQL_UPDATE_COMMISSION_INFO = "update credit_apply set commission_img_url = ?, apply_state = ?, update_time = current_timestamp where id = ?";

	private static final String SQL_UPDATE_APPLY_APPROVE_INFO = "update credit_apply set due_time = ?, apply_state = ?, effective_time = current_timestamp, approve_time = current_timestamp, update_time = current_timestamp where id = ? and apply_state = 'Approving'";

	private static final String SQL_UPDATE_APPLY_APPROVE_REJECT_INFO = "update credit_apply set approve_note = ?, apply_state = ?, approve_time = current_timestamp, update_time = current_timestamp where id = ? and apply_state = 'Approving'";

	private static final String SQL_UPDATE_APPLY_RETURN_INFO = "update credit_apply set returned_base = ?, apply_state = ?, update_time = current_timestamp where id = ? and apply_state = 'Approved'";
	
	private static final String SQL_UPDATE_APPLY_OVERDUE = "update credit_apply set apply_state = 'Overdue', update_time = current_timestamp where apply_state = 'Approved' and due_time < current_timestamp ";
	
	private static final String SQL_DELETE_APPLY = "delete from credit_apply where id = ? and apply_state = 'Applying'";

	private static final String SQL_GET_CREDIT_APPLY_PREFIX = "select * from credit_apply ";

	private static final String SQL_GET_APPLY_BY_ID = SQL_GET_CREDIT_APPLY_PREFIX
			+ "where id = ?";

	private static final String SQL_GET_APPLY_LIST = SQL_GET_CREDIT_APPLY_PREFIX
			+ "where 1 = 1 ";

	private static final DaoRowMapper<CreditApply> creditApplyMapper = new DaoRowMapper<CreditApply>(
			CreditApply.class);

	@Override
	public void saveCreditApply(CreditApply creditApply) {
		String errMsg = "Failed to save new credit appy, userId: "
				+ creditApply.getUserId();
		long id = daoHelper.save(SQL_SAVE_CREDIT_APPLY, errMsg, true,
				creditApply.getUserId(), creditApply.getAmount(), creditApply.getMonth(),
				creditApply.getInterest(), creditApply.getCommission(),
				creditApply.getApplyState().toString(),
				creditApply.getApplyTime());
		creditApply.setId(id);
	}

	@Override
	public void updateCommissionInfo(CreditApply creditApply) {

		String errMsg = "Failed to update credit apply commission info, id: "
				+ creditApply.getId();
		daoHelper.update(SQL_UPDATE_COMMISSION_INFO, errMsg,
				creditApply.getCommissionImgUrl(), creditApply.getApplyState().toString(), creditApply.getId());
	}

	@Override
	public void updateApplyApproveInfo(long applyId, String note,
			ApplyState toState) {

		String errMsg = "Failed to update credit apply state, id: " + applyId;
		daoHelper.update(SQL_UPDATE_APPLY_APPROVE_REJECT_INFO, errMsg, note,
				toState.toString(), applyId);
	}

	@Override
	public void updateApplyApproveInfo(CreditApply apply) {

		String errMsg = "Failed to update credit apply state, id: "
				+ apply.getId();
		daoHelper.update(SQL_UPDATE_APPLY_APPROVE_INFO, errMsg,
				apply.getDueTime(), apply.getApplyState().toString(),
				apply.getId());
	}

	@Override
	public CreditApply getApply(long applyId) {

		String errMsg = "Failed to get credit apply by id: " + applyId;
		CreditApply creditApply = daoHelper.queryForObject(SQL_GET_APPLY_BY_ID,
				creditApplyMapper, errMsg, applyId);

		return creditApply;
	}

	@Override
	public List<CreditApply> getApplyList(long userId, List<ApplyState> applyStates, Date toDate, boolean asc) {

		String errMsg = "Failed to get credit apply of user: " + userId;
		
		String sql = SQL_GET_APPLY_LIST;
		
		List<Object> args = new ArrayList<Object>();
		if (userId > 0) {
			sql += "user_id = ?";
			args.add(userId);
		}
		
		if (CollectionUtils.isNotEmpty(applyStates)) {
			String stateSql = " and apply_state in (" + StringUtils.getCSV(applyStates, true) + ")";
			sql += stateSql;
		}
		
		if (toDate != null) {
			String toDateSql = " and due_time <= ?";
			sql += toDateSql;
			
			args.add(toDate);
		}
		
		sql += (asc ? " order by id asc" : " order by id desc");
		
		
		List<CreditApply> creditApplies = daoHelper.queryForList(
				sql, creditApplyMapper, errMsg, args.toArray(new Object[0]));

		return creditApplies;
	}

	@Override
	public void updateReturnInfo(CreditApply apply) {

		String errMsg = "Failed to update credit apply returned info, id: "
				+ apply.getId();
		daoHelper.update(SQL_UPDATE_APPLY_RETURN_INFO, errMsg,
				apply.getReturnedBase(), apply.getApplyState().toString(),
				apply.getId());
	}

	@Override
	public void updateOverdueState() {

		String errMsg = "Failed to update credit apply to overdue.";
		daoHelper.update(SQL_UPDATE_APPLY_OVERDUE, errMsg);
	}

	@Override
	public void deleteApply(long applyId) {
		String errMsg = "Failed to update credit apply to overdue.";
		daoHelper.update(SQL_DELETE_APPLY, errMsg);
	}

}

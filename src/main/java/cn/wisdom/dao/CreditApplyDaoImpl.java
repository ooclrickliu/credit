package cn.wisdom.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.wisdom.dao.constant.ApplyState;
import cn.wisdom.dao.mapper.DaoRowMapper;
import cn.wisdom.dao.vo.CreditApply;

@Repository
public class CreditApplyDaoImpl implements CreditApplyDao {

	@Autowired
	private DaoHelper daoHelper;

	private static final String SQL_SAVE_CREDIT_APPLY = "insert ignore into credit_apply(user_id, amount, interest, commission, apply_state, apply_time, update_time) values (?, ?, ?, ?, ?, ?, current_timestamp)";

	private static final String SQL_UPDATE_COMMISSION_INFO = "update credit_apply set commission_img_url = ?, update_time = current_timestamp where id = ?";

	private static final String SQL_UPDATE_APPLY_APPROVE_INFO = "update credit_apply set due_time = ?, apply_state = ?, update_time = current_timestamp where id = ? and apply_state = 'Approving'";

	private static final String SQL_UPDATE_APPLY_APPROVE_REJECT_INFO = "update credit_apply set approve_note = ?, apply_state = ?, update_time = current_timestamp where id = ? and apply_state = 'Approving'";

	private static final String SQL_UPDATE_APPLY_RETURN_INFO = "update credit_apply set returned_base = ?, apply_state = ?, update_time = current_timestamp where id = ? and apply_state = 'Approved'";

	private static final String SQL_GET_CREDIT_APPLY_PREFIX = "select * from credit_apply ";

	private static final String SQL_GET_APPLY_BY_ID = SQL_GET_CREDIT_APPLY_PREFIX
			+ "where id = ?";

	private static final String SQL_GET_APPLY_LIST_BY_USER = SQL_GET_CREDIT_APPLY_PREFIX
			+ "where user_id = ?";

	private static final DaoRowMapper<CreditApply> creditApplyMapper = new DaoRowMapper<CreditApply>(
			CreditApply.class);

	@Override
	public void saveCreditApply(CreditApply creditApply) {
		String errMsg = "Failed to save new credit appy, userId: "
				+ creditApply.getUserId();
		daoHelper.save(SQL_SAVE_CREDIT_APPLY, errMsg, false,
				creditApply.getUserId(), creditApply.getAmount(),
				creditApply.getInterest(), creditApply.getCommission(),
				creditApply.getApplyState().toString(),
				creditApply.getApplyTime());
	}

	@Override
	public void updateCommissionInfo(CreditApply creditApply) {

		String errMsg = "Failed to update credit apply commission info, id: "
				+ creditApply.getId();
		daoHelper.update(SQL_UPDATE_COMMISSION_INFO, errMsg,
				creditApply.getCommissionImgUrl(), creditApply.getId());
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
	public List<CreditApply> getApplyList(long userId) {

		String errMsg = "Failed to get credit apply of user: " + userId;
		List<CreditApply> creditApplies = daoHelper.queryForList(
				SQL_GET_APPLY_LIST_BY_USER, creditApplyMapper, errMsg, userId);

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

}

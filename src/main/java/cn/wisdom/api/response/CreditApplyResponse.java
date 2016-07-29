package cn.wisdom.api.response;

import java.util.List;

import cn.wisdom.dao.vo.CreditApply;
import cn.wisdom.dao.vo.CreditPayRecord;
import cn.wisdom.dao.vo.User;

public class CreditApplyResponse {

	private CreditApply creditApply;
	
	private User user;
	
	private List<CreditPayRecord> payRecords;

	public CreditApply getCreditApply() {
		return creditApply;
	}

	public void setCreditApply(CreditApply creditApply) {
		this.creditApply = creditApply;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<CreditPayRecord> getPayRecords() {
		return payRecords;
	}

	public void setPayRecords(List<CreditPayRecord> payRecords) {
		this.payRecords = payRecords;
	}
}

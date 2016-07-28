package cn.wisdom.api.response;

import cn.wisdom.dao.vo.CreditApply;
import cn.wisdom.dao.vo.User;

public class CreditApplyResponse {

	private CreditApply creditApply;
	
	private User user;

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
}

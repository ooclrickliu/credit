package cn.wisdom.dao.vo;

import java.sql.Timestamp;

import cn.wisdom.dao.annotation.Column;
import cn.wisdom.dao.constant.ApplyState;

public class CreditPayRecord extends CreditApply {

	@Column("applyId")
    private long applyId;
	
	@Column("credit_base")
	private float creditBase;
	
	@Column("credit_interest")
	private float interest;
	
	@Column("returned_amount")
	private float returnedAmount;
	
	@Column("remain_base")
	private float remainBase;
	
	@Column("return_state")
	private String returnStateValue;
	private ApplyState returnState;
	
	@Column("return_time")
	private Timestamp returnTime;

	public long getApplyId() {
		return applyId;
	}

	public void setApplyId(long applyId) {
		this.applyId = applyId;
	}

	public float getInterest() {
		return interest;
	}

	public void setInterest(float interest) {
		this.interest = interest;
	}

	public float getReturnedAmount() {
		return returnedAmount;
	}

	public void setReturnedAmount(float returnedAmount) {
		this.returnedAmount = returnedAmount;
	}

	public ApplyState getReturnState() {
		if (returnState == null) {
			returnState = ApplyState.valueOf(returnStateValue);
		}
		
		return returnState;
	}

	public void setReturnState(ApplyState returnState) {
		this.returnState = returnState;
	}

	public Timestamp getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Timestamp returnTime) {
		this.returnTime = returnTime;
	}

	public float getCreditBase() {
		return creditBase;
	}

	public void setCreditBase(float creditBase) {
		this.creditBase = creditBase;
	}

	public float getRemainBase() {
		return remainBase;
	}

	public void setRemainBase(float remainBase) {
		this.remainBase = remainBase;
	}

	public String getReturnStateValue() {
		return returnStateValue;
	}

	public void setReturnStateValue(String returnStateValue) {
		this.returnStateValue = returnStateValue;
	}
}

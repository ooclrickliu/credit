package cn.wisdom.dao.vo;

import java.sql.Timestamp;

import org.springframework.web.multipart.MultipartFile;

import cn.wisdom.dao.annotation.Column;
import cn.wisdom.dao.constant.ApplyState;

public class CreditApply extends BaseEntity {

	@Column("userId")
    private long userId;
	
	@Column("amount")
	private float amount;
	
	@Column("month")
	private int month;
	
	@Column("interest")
	private float interest;
	
	@Column("commission")
	private float commission;
	
	@Column("commission_img_url")
	private String commissionImgUrl;
	private MultipartFile commissionImg;
	
	@Column("apply_state")
	private String applyStateValue;
	private ApplyState applyState;
	
	@Column("apply_time")
	private Timestamp applyTime;
	
	@Column("approve_time")
	private Timestamp approveTime;
	
	@Column("approve_note")
	private String approveNote;
	
	@Column("effective_time")
	private Timestamp effectiveTime;
	
	@Column("due_time")
	private Timestamp dueTime;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public float getCommission() {
		return commission;
	}

	public void setCommission(float commission) {
		this.commission = commission;
	}

	public ApplyState getApplyState() {
		if (applyState == null) {
			applyState = ApplyState.valueOf(applyStateValue);
		}
		return applyState;
	}

	public void setApplyState(ApplyState applyState) {
		this.applyState = applyState;
	}

	public Timestamp getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Timestamp applyTime) {
		this.applyTime = applyTime;
	}

	public Timestamp getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Timestamp approveTime) {
		this.approveTime = approveTime;
	}

	public String getApproveNote() {
		return approveNote;
	}

	public void setApproveNote(String approveNote) {
		this.approveNote = approveNote;
	}

	public Timestamp getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(Timestamp effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public Timestamp getDueTime() {
		return dueTime;
	}

	public void setDueTime(Timestamp dueTime) {
		this.dueTime = dueTime;
	}

	public String getCommissionImgUrl() {
		return commissionImgUrl;
	}

	public void setCommissionImgUrl(String commissionImgUrl) {
		this.commissionImgUrl = commissionImgUrl;
	}

	public MultipartFile getCommissionImg() {
		return commissionImg;
	}

	public void setCommissionImg(MultipartFile commissionImg) {
		this.commissionImg = commissionImg;
	}

	public float getInterest() {
		return interest;
	}

	public void setInterest(float interest) {
		this.interest = interest;
	}

}

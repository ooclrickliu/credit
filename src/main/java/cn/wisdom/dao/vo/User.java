/**
 * User.java
 * 
 * Copyright@2015 OVT Inc. All rights reserved. 
 * 
 * May 4, 2015
 */
package cn.wisdom.dao.vo;

import java.sql.Timestamp;

import me.chanjar.weixin.mp.bean.result.WxMpUser;
import cn.wisdom.common.utils.StringUtils;
import cn.wisdom.dao.annotation.Column;
import cn.wisdom.dao.constant.Degree;
import cn.wisdom.dao.constant.MaritalStatus;
import cn.wisdom.dao.constant.RoleType;
import cn.wisdom.dao.constant.UserState;

/**
 * User2
 * 
 * @Author zhi.liu
 * @Version 1.0
 * @See
 * @Since [OVT Cloud Platform]/[DAO] 1.0
 */
public class User extends BaseEntity
{
	@Column("openid")
    private String openid;

	@Column("role")
    private String roleValue;
	private RoleType role;
	
	// step1
	@Column("real_name")
	private String realName;

	@Column("id_face_img_url")
	private String idFaceImgUrl;
	
	@Column("id_back_img_url")
	private String idBackImgUrl;
	
	@Column("person_id_img_url")
	private String personIdImgUrl;
	
	@Column("address")
    private String address;
	
	@Column("marital_status")
	private String maritalStatusValue;
	private MaritalStatus maritalStatus;
	
	@Column("degree")
	private String degreeValue;
	private Degree degree;

	// step2
	@Column("relative_name1")
    private String relativeName1;
	
	@Column("relative_relation1")
	private String relativeRelation1;
	
	@Column("relative_phone1")
	private String relativePhone1;
	
	@Column("relative_name2")
	private String relativeName2;
	
	@Column("relative_relation2")
	private String relativeRelation2;
	
	@Column("relative_phone2")
	private String relativePhone2;
	
	// step3:
	@Column("phone")
	private String phone;
	
	@Column("phone_password")
	private String phonePassword;
	
	@Column("account_no")
	private String accountNo;
	
	// step4:
	@Column("wx_pay_img_url")
	private String wxPayImgUrl;
	
	private boolean step1Done;
	
	private boolean step2Done;
	
	private boolean step3Done;
	
	private boolean step4Done;
	
	// wx
	@Column("nick_name")
    private String nickName;

	@Column("head_img_url")
    private String headImgUrl;
	
	// other:
	@Column("approve_time")
    private Timestamp approveTime;
	
	@Column("approve_note")
	private String approveNote;
	
	/**
	 * 0: 关注用户，且资料不完善
	 * 1: 1星级用户
	 * 2: 2星级用户
	 * 3: 3星级用户
	 * 4: 4星级用户
	 * 5: 5星级用户
	 */
	@Column("level")
	private int level;  
	
	@Column("credit_line")
	private float creditLine;
	
	@Column("user_state")
	private String userStateValue;
	private UserState userState;
	
	public User() {}
	
	public User(WxMpUser wxMpUser) {
		this.openid = wxMpUser.getOpenId();
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public RoleType getRole() {
		return RoleType.valueOf(this.roleValue);
	}

	public void setRole(RoleType role) {
		this.role = role;
		this.roleValue = this.role.toString();
	}

	public String getRoleValue() {
		return roleValue;
	}

	public void setRoleValue(String roleValue) {
		this.roleValue = roleValue;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIdFaceImgUrl() {
		return idFaceImgUrl;
	}

	public void setIdFaceImgUrl(String idFaceImgUrl) {
		this.idFaceImgUrl = idFaceImgUrl;
	}

	public String getIdBackImgUrl() {
		return idBackImgUrl;
	}

	public void setIdBackImgUrl(String idBackImgUrl) {
		this.idBackImgUrl = idBackImgUrl;
	}

	public String getPersonIdImgUrl() {
		return personIdImgUrl;
	}

	public void setPersonIdImgUrl(String personIdImgUrl) {
		this.personIdImgUrl = personIdImgUrl;
	}

	public MaritalStatus getMaritalStatus() {
		if (maritalStatus == null && StringUtils.isNotBlank(maritalStatusValue)) {
			maritalStatus = MaritalStatus.valueOf(maritalStatusValue);
		}
		
		return maritalStatus;
	}

	public void setMaritalStatus(MaritalStatus maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public void setDegreeValue(String degreeValue) {
		this.degreeValue = degreeValue;
	}

	public Degree getDegree() {
		if (degree == null && StringUtils.isNotBlank(degreeValue)) {
			degree = Degree.valueOf(degreeValue);
		}
		return degree;
	}

	public void setDegree(Degree degree) {
		this.degree = degree;
	}

	public String getRelativeName1() {
		return relativeName1;
	}

	public void setRelativeName1(String relativeName1) {
		this.relativeName1 = relativeName1;
	}

	public String getRelativeRelation1() {
		return relativeRelation1;
	}

	public void setRelativeRelation1(String relativeRelation1) {
		this.relativeRelation1 = relativeRelation1;
	}

	public String getRelativePhone1() {
		return relativePhone1;
	}

	public void setRelativePhone1(String relativePhone1) {
		this.relativePhone1 = relativePhone1;
	}

	public String getRelativeName2() {
		return relativeName2;
	}

	public void setRelativeName2(String relativeName2) {
		this.relativeName2 = relativeName2;
	}

	public String getRelativeRelation2() {
		return relativeRelation2;
	}

	public void setRelativeRelation2(String relativeRelation2) {
		this.relativeRelation2 = relativeRelation2;
	}

	public String getRelativePhone2() {
		return relativePhone2;
	}

	public void setRelativePhone2(String relativePhone2) {
		this.relativePhone2 = relativePhone2;
	}

	public String getPhonePassword() {
		return phonePassword;
	}

	public void setPhonePassword(String phonePassword) {
		this.phonePassword = phonePassword;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getWxPayImgUrl() {
		return wxPayImgUrl;
	}

	public void setWxPayImgUrl(String wxPayImgUrl) {
		this.wxPayImgUrl = wxPayImgUrl;
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

	public float getCreditLine() {
		return creditLine;
	}

	public void setCreditLine(float creditLine) {
		this.creditLine = creditLine;
	}

	public UserState getUserState() {
		if (userState == null && StringUtils.isNotBlank(userStateValue)) {
			userState = UserState.valueOf(userStateValue);
		}
		
		return userState;
	}

	public void setUserState(UserState userState) {
		this.userState = userState;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUserStateValue() {
		return userStateValue;
	}

	public void setUserStateValue(String userStateValue) {
		this.userStateValue = userStateValue;
	}

	public String getDegreeValue() {
		return degreeValue;
	}

	public boolean isStep1Done() {
		step1Done = StringUtils.isNotBlank(this.getRealName())
				&& StringUtils.isNotBlank(this.getIdBackImgUrl())
				&& StringUtils.isNotBlank(this.getIdBackImgUrl())
				&& StringUtils.isNotBlank(this.getPersonIdImgUrl())
				&& StringUtils.isNotBlank(this.getAddress())
				&& this.getMaritalStatus() != null && this.getDegree() != null;
		
		return step1Done;
	}

	public void setStep1Done(boolean step1Done) {
		this.step1Done = step1Done;
	}

	public boolean isStep2Done() {
		step2Done = StringUtils.isNotBlank(this.getRelativeName1())
				&& StringUtils.isNotBlank(this.getRelativeName2())
				&& StringUtils.isNotBlank(this.getRelativeRelation1())
				&& StringUtils.isNotBlank(this.getRelativeRelation2())
				&& StringUtils.isNotBlank(this.getRelativePhone1())
				&& StringUtils.isNotBlank(this.getRelativePhone2());
		
		return step2Done;
	}

	public void setStep2Done(boolean step2Done) {
		this.step2Done = step2Done;
	}

	public boolean isStep3Done() {
		step3Done = StringUtils.isNotBlank(this.getPhone())
				&& StringUtils.isNotBlank(this.getPhonePassword())
				&& StringUtils.isNotBlank(this.getAccountNo());
		
		return step3Done;
	}

	public void setStep3Done(boolean step3Done) {
		this.step3Done = step3Done;
	}

	public boolean isStep4Done() {
		step4Done = StringUtils.isNotBlank(this.getWxPayImgUrl());
		
		return step4Done;
	}

	public void setStep4Done(boolean step4Done) {
		this.step4Done = step4Done;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}

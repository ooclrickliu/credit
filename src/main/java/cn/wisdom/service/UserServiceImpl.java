/**
 * UserServiceImpl.java
 * 
 * Copyright@2015 OVT Inc. All rights reserved. 
 * 
 * May 5, 2015
 */
package cn.wisdom.service;

import java.util.List;
import java.util.Random;

import me.chanjar.weixin.mp.bean.result.WxMpUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.wisdom.common.log.Logger;
import cn.wisdom.common.log.LoggerFactory;
import cn.wisdom.common.utils.StringUtils;
import cn.wisdom.dao.UserDao;
import cn.wisdom.dao.constant.RoleType;
import cn.wisdom.dao.constant.UserState;
import cn.wisdom.dao.vo.User;
import cn.wisdom.service.exception.ServiceException;

/**
 * UserServiceImpl
 * 
 * @Author zhi.liu
 * @Version 1.0
 * @See
 * @Since [OVT Cloud Platform]/[API] 1.0
 */
@Service
public class UserServiceImpl implements UserService
{
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private FileUploadService fileUploadService;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class.getName());

	@Override
	public void createUser(String openId, RoleType role) throws ServiceException {
		User user = new User();
		
		user.setRole(role);
		user.setOpenid(openId);
		
		//关注即有3000额度
		user.setCreditLine(3000);
		
		user.setUserState(UserState.Subscribe);
		
		userDao.save(user);
	}

	@Override
	public void updateUserWxInfo(WxMpUser wxMpUser) throws ServiceException {
		User user = new User();
		user.setOpenid(wxMpUser.getOpenId());
		user.setNickName(wxMpUser.getNickname());
		user.setHeadImgUrl(wxMpUser.getHeadImgUrl());
		
		userDao.updateUserWxInfo(user);
	}

	@Override
	public User getUserByOpenId(String openId) {
		
		return userDao.getUserByOpenid(openId);
	}

	@Override
	public void submitStuffStep1(User user) {
		
		if (isStuffInfo1Complete(user)) {
			setUserCreditLine(user);
			
			// save upload file
			String fileName = "" + user.getId() + System.currentTimeMillis();
			String idFaceImgUrl = fileUploadService.saveUploadFile(user.getIdFaceImg(), fileName);
			user.setIdFaceImgUrl(idFaceImgUrl);
			
			fileName = "" + user.getId() + System.currentTimeMillis();
			String idBackImgUrl = fileUploadService.saveUploadFile(user.getIdBackImg(), fileName);
			user.setIdBackImgUrl(idBackImgUrl);
			
			fileName = "" + user.getId() + System.currentTimeMillis();
			String personIdImgUrl = fileUploadService.saveUploadFile(user.getPersonIdImg(), fileName);
			user.setPersonIdImgUrl(personIdImgUrl);
			
			userDao.updateUserStuffInfo1(user);
		}
	}
	
	@Override
	public void submitStuffStep2(User user) {

		if (isStuffInfo2Complete(user)) {
			setUserCreditLine(user);
			
			userDao.updateUserStuffInfo2(user);
		}
	}

	@Override
	public void submitStuffStep3(User user) {

		if (isStuffInfo3Complete(user)) {
			setUserCreditLine(user);
			
			userDao.updateUserStuffInfo3(user);
		}
	}

	@Override
	public void submitStuffStep4(User user) {
		
		if (isStuffInfo4Complete(user)) {
			setUserCreditLine(user);
			
			// save upload file
			String fileName = "" + user.getId() + System.currentTimeMillis();
			String wxPayImgUrl = fileUploadService.saveUploadFile(user.getWxPayImg(), fileName);
			user.setWxPayImgUrl(wxPayImgUrl);
			
			userDao.updateUserStuffInfo4(user);
		}
	}
	
	private void setUserCreditLine(User user)
	{
		if (isStuffComplete(user)) {
			user.setCreditLine(generateCreditLine());
			
			user.setUserState(UserState.New);
		}
	}
	
	private boolean isStuffComplete(User user) {
		
		return isStuffInfo1Complete(user) && isStuffInfo2Complete(user) && isStuffInfo3Complete(user) && 
				isStuffInfo4Complete(user);
	}
	
	private boolean isStuffInfo4Complete(User user)
	{
		return StringUtils.isNotBlank(user.getWxPayImgUrl());
	}
	
	private boolean isStuffInfo3Complete(User user)
	{
		return StringUtils.isNotBlank(user.getPhone()) && 
				StringUtils.isNotBlank(user.getPhonePassword()) && StringUtils.isNotBlank(user.getAccountNo());
	}
	
	private boolean isStuffInfo2Complete(User user)
	{
		return StringUtils.isNotBlank(user.getRelativeName1()) && 
				StringUtils.isNotBlank(user.getRelativeName2()) && StringUtils.isNotBlank(user.getRelativeRelation1()) && 
				StringUtils.isNotBlank(user.getRelativeRelation2()) && StringUtils.isNotBlank(user.getRelativePhone1()) && 
				StringUtils.isNotBlank(user.getRelativePhone2());
	}
	
	private boolean isStuffInfo1Complete(User user)
	{
		return StringUtils.isNotBlank(user.getRealName()) && StringUtils.isNotBlank(user.getIdBackImgUrl()) && 
				StringUtils.isNotBlank(user.getIdBackImgUrl()) && StringUtils.isNotBlank(user.getPersonIdImgUrl()) && 
				StringUtils.isNotBlank(user.getProvince()) && StringUtils.isNotBlank(user.getCity()) && StringUtils.isNotBlank(user.getDistinct()) && 
				user.getMaritalStatus() != null && user.getDegree() != null;
	}

	private float generateCreditLine()
	{
		Random random = new Random();
		
		return random.nextInt(7000) + 3000;
	}

	@Override
	public List<User> getUserList(UserState userState) {
		return userDao.getUserList(userState);
	}

}

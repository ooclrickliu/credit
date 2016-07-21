/**
 * UserServiceImpl.java
 * 
 * Copyright@2015 OVT Inc. All rights reserved. 
 * 
 * May 5, 2015
 */
package cn.wisdom.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import me.chanjar.weixin.mp.bean.result.WxMpUser;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.wisdom.common.log.Logger;
import cn.wisdom.common.log.LoggerFactory;
import cn.wisdom.common.utils.StringUtils;
import cn.wisdom.dao.UserDao;
import cn.wisdom.dao.constant.RoleType;
import cn.wisdom.dao.constant.UserState;
import cn.wisdom.dao.vo.AppProperty;
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
	private AppProperty appProperty;

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
			String idFaceImgUrl = saveUploadFile(user.getIdFaceImg(), "" + user.getId());
			user.setIdFaceImgUrl(idFaceImgUrl);
			String idBackImgUrl = saveUploadFile(user.getIdBackImg(), "" + user.getId());
			user.setIdBackImgUrl(idBackImgUrl);
			String personIdImgUrl = saveUploadFile(user.getPersonIdImg(), "" + user.getId());
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
			String wxPayImgUrl = saveUploadFile(user.getWxPayImg(), "" + user.getId());
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

	private String saveUploadFile(MultipartFile multipartFile, String id) {
		String dir = appProperty.fileUploadDir;
		
		String fileName = id + System.currentTimeMillis();
		File file = new File(dir + fileName);
		
		try {
			FileUtils.writeByteArrayToFile(file, multipartFile.getBytes());
		} catch (IOException e) {
			logger.error("Failed to save upload file - " + multipartFile.getName(), e);
		}
		
		return fileName;
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

/**
 * UserServiceImpl.java
 * 
 * Copyright@2015 OVT Inc. All rights reserved. 
 * 
 * May 5, 2015
 */
package cn.wisdom.service;

import java.io.File;
import java.text.MessageFormat;
import java.util.List;
import java.util.Random;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.wisdom.common.log.Logger;
import cn.wisdom.common.log.LoggerFactory;
import cn.wisdom.common.utils.StringUtils;
import cn.wisdom.dao.UserDao;
import cn.wisdom.dao.constant.RoleType;
import cn.wisdom.dao.constant.UserState;
import cn.wisdom.dao.vo.AppProperty;
import cn.wisdom.dao.vo.User;
import cn.wisdom.service.exception.ServiceException;
import cn.wisdom.service.wx.WXService;

/**
 * UserServiceImpl
 * 
 * @Author zhi.liu
 * @Version 1.0
 * @See
 * @Since [OVT Cloud Platform]/[API] 1.0
 */
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	@Autowired
	private WXService wxService;

	@Autowired
	private AppProperty appProperty;

	private static final Logger logger = LoggerFactory
			.getLogger(UserServiceImpl.class.getName());

	@Override
	public void createUser(String openId, RoleType role)
			throws ServiceException {
		User user = new User();

		user.setRole(role);
		user.setOpenid(openId);

		// 关注即有3000额度
		user.setCreditLine(appProperty.defaultCreditLine);
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
	public User getUserByOauthCode(String oauthCode) {
		User user = null;
		try {
			WxMpOAuth2AccessToken oauth2getAccessToken = wxService.getWxMpService().oauth2getAccessToken(oauthCode);
			
			System.out.println("----------oauth:" + oauth2getAccessToken);
			
//			WxMpUser wxMpUser = wxService.getWxMpService().oauth2getUserInfo(oauth2getAccessToken, null);
			WxMpUser wxMpUser = wxService.getWxMpService().userInfo(oauth2getAccessToken.getOpenId(), null);
			
			user = userDao.getUserByOpenid(wxMpUser.getOpenId());
			if (user != null && !(StringUtils.equals(user.getNickName(), wxMpUser.getNickname()) &&
					StringUtils.equals(user.getHeadImgUrl(), wxMpUser.getHeadImgUrl()))) {
				user.setNickName(wxMpUser.getNickname());
				user.setHeadImgUrl(wxMpUser.getHeadImgUrl());
				
				userDao.updateUserWxInfo(user);
			}
		} catch (WxErrorException e) {
			String errMsg = MessageFormat.format("failed pass wx oauth and get user info, code: [{0}]", oauthCode);
			logger.error(errMsg, e);
		}
		return user;
	}

	@Override
	public void submitStuffStep1(User user) {

		if (isStuffInfo1Complete(user)) {
			setUserCreditLine(user);

			// save upload file
			try {
				File idFaceImg = wxService.getWxMpService().mediaDownload(
						user.getIdFaceImgUrl());
				user.setIdFaceImgUrl(idFaceImg.getAbsolutePath());
			} catch (WxErrorException e) {
				logger.error("failed to upload idFaceImg", e);
			}

			try {
				File idBackImg = wxService.getWxMpService().mediaDownload(
						user.getIdBackImgUrl());
				user.setIdBackImgUrl(idBackImg.getAbsolutePath());
			} catch (WxErrorException e) {
				logger.error("failed to upload idBackImg", e);
			}

			try {
				File personIdImg = wxService.getWxMpService().mediaDownload(
						user.getPersonIdImgUrl());
				user.setPersonIdImgUrl(personIdImg.getAbsolutePath());
			} catch (WxErrorException e) {
				logger.error("failed to upload personIdImg", e);
			}

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
			try {
				File wxPayImg = wxService.getWxMpService().mediaDownload(
						user.getWxPayImgUrl());
				user.setWxPayImgUrl(wxPayImg.getAbsolutePath());

				userDao.updateUserStuffInfo4(user);
			} catch (WxErrorException e) {
				logger.error("failed to upload wxPayImg", e);
			}
		}
	}

	private void setUserCreditLine(User user) {
		if (isStuffComplete(user)) {
			user.setCreditLine(generateCreditLine());

			user.setUserState(UserState.New);
		}
	}

	private boolean isStuffComplete(User user) {

		return isStuffInfo1Complete(user) && isStuffInfo2Complete(user)
				&& isStuffInfo3Complete(user) && isStuffInfo4Complete(user);
	}

	private boolean isStuffInfo4Complete(User user) {
		return StringUtils.isNotBlank(user.getWxPayImgUrl());
	}

	private boolean isStuffInfo3Complete(User user) {
		return StringUtils.isNotBlank(user.getPhone())
				&& StringUtils.isNotBlank(user.getPhonePassword())
				&& StringUtils.isNotBlank(user.getAccountNo());
	}

	private boolean isStuffInfo2Complete(User user) {
		return StringUtils.isNotBlank(user.getRelativeName1())
				&& StringUtils.isNotBlank(user.getRelativeName2())
				&& StringUtils.isNotBlank(user.getRelativeRelation1())
				&& StringUtils.isNotBlank(user.getRelativeRelation2())
				&& StringUtils.isNotBlank(user.getRelativePhone1())
				&& StringUtils.isNotBlank(user.getRelativePhone2());
	}

	private boolean isStuffInfo1Complete(User user) {
		return StringUtils.isNotBlank(user.getRealName())
				&& StringUtils.isNotBlank(user.getIdBackImgUrl())
				&& StringUtils.isNotBlank(user.getIdBackImgUrl())
				&& StringUtils.isNotBlank(user.getPersonIdImgUrl())
				&& StringUtils.isNotBlank(user.getAddress())
				&& user.getMaritalStatus() != null && user.getDegree() != null;
	}

	private float generateCreditLine() {
		Random random = new Random();

		return random
				.nextInt((int) (appProperty.maxCreditLine - appProperty.minCreditLine))
				+ appProperty.minCreditLine;
	}

	@Override
	public List<User> getUserList(UserState userState) {
		return userDao.getUserList(userState);
	}

}

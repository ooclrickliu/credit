/**
 * UserService.java
 * 
 * Copyright@2015 OVT Inc. All rights reserved. 
 * 
 * May 5, 2015
 */
package cn.wisdom.service;

import java.util.List;

import me.chanjar.weixin.mp.bean.result.WxMpUser;
import cn.wisdom.dao.constant.RoleType;
import cn.wisdom.dao.constant.UserState;
import cn.wisdom.dao.vo.User;
import cn.wisdom.service.exception.ServiceException;

/**
 * UserService
 * 
 * @Author zhi.liu
 * @Version 1.0
 * @See
 * @Since [OVT Cloud Platform]/[Service] 1.0
 */
public interface UserService
{
	/**
	 * 保存新关注用户
	 * 
	 * @param openId
	 * @param role TODO
	 * @throws ServiceException
	 */
	void createUser(String openId, RoleType role) throws ServiceException;
	
	/**
	 * 更新用户基本信息, 基本信息通过OAuth2.0获得
	 * 
	 * @param wxMpUser
	 * @throws ServiceException
	 */
	void updateUserWxInfo(WxMpUser wxMpUser) throws ServiceException;

	/**
	 * 查询用户
	 * 
	 * @param openId
	 * @return
	 */
	User getUserByOpenId(String openId);
	
	/**
	 * 通过OAuth获取用户信息
	 * 
	 * @param oauthCode
	 * @return
	 */
	User getUserByOauthCode(String oauthCode);

	/**
	 * 通过ID获取用户信息
	 * 
	 * @param userId
	 * @return
	 */
	User getUserById(long userId);
	
	/////////////////////////////////////////////


	void submitStuffStep1(User user);
	
	void submitStuffStep2(User user);
	
	void submitStuffStep3(User user);
	
	void submitStuffStep4(User user);
	
	List<User> getUserList(UserState userState);

	void approve(User user);
	
	/////////////////////////////////////////////
	
	String login(String phone, String password) throws ServiceException;
	
    void logout(String accessToken) throws ServiceException;

	String changePassword(String oldPassword, String newPassword) throws ServiceException;
    
}

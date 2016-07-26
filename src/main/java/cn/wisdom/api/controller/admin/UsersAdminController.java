/**
 * UsersController.java
 * 
 * Copyright@2015 OVT Inc. All rights reserved. 
 * 
 * May 4, 2015
 */
package cn.wisdom.api.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wisdom.api.response.CreditAPIResult;
import cn.wisdom.common.model.JsonDocument;
import cn.wisdom.common.utils.StringUtils;
import cn.wisdom.dao.constant.UserState;
import cn.wisdom.dao.vo.User;
import cn.wisdom.service.UserService;
import cn.wisdom.service.context.SessionContext;
import cn.wisdom.service.exception.ServiceException;

/**
 * UsersController provides restful APIs of user
 * 
 * @Author zhi.liu
 * @Version 1.0
 * @See
 * @Since [OVT Cloud Platform]/[API] 1.0
 */

@Controller
@RequestMapping("/admin/users")
public class UsersAdminController
{
    @Autowired
    private UserService userService;

    /**
     * Get current User by oauth code.
     * 
     * @return
     * @throws ServiceException
     */
    @RequestMapping(method = RequestMethod.GET, value = "/current")
    @ResponseBody
    public JsonDocument getCurrentUser() throws ServiceException
    {
    	User user = SessionContext.getCurrentUser();
        return new CreditAPIResult(user);
    }
    
    /**
     * 根据状态查询用户列表.
     * 
     * @return
     * @throws ServiceException
     */
    @RequestMapping(method = RequestMethod.GET, value = "/list")
    @ResponseBody
    public JsonDocument getUserList(@RequestParam String state) throws ServiceException
    {
    	List<User> userList = null;
    	if (StringUtils.isNotBlank(state)) {
    		UserState userState = UserState.valueOf(state);
    		
    		userList = userService.getUserList(userState);
		}
    	
    	return new CreditAPIResult(userList);
    }
    
    /**
     * 根据状态查询用户列表.
     * 
     * @return
     * @throws ServiceException
     */
    @RequestMapping(method = RequestMethod.GET, value = "/approve")
    @ResponseBody
    public JsonDocument approve(@RequestBody User user) throws ServiceException
    {
    	User selectedUser = userService.getUserById(user.getId());
    	
    	selectedUser.setUserState(UserState.Approved);
    	selectedUser.setLevel(user.getLevel());
    	selectedUser.setApproveNote(user.getApproveNote());
    	
    	userService.approve(selectedUser);
    	
    	return new CreditAPIResult(selectedUser);
    }
    
}

/**
 * UsersController.java
 * 
 * Copyright@2015 OVT Inc. All rights reserved. 
 * 
 * May 4, 2015
 */
package cn.wisdom.api.controller;

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
import cn.wisdom.dao.vo.AppProperty;
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
@RequestMapping("/users")
public class UsersController
{
    @Autowired
    private UserService userService;

    private static final JsonDocument SUCCESS = CreditAPIResult.SUCCESS;
    @Autowired
    private AppProperty appProperties;

    /**
     * Get current User by oauth code.
     * 
     * @return
     * @throws ServiceException
     */
    @RequestMapping(method = RequestMethod.GET, value = "/current")
    @ResponseBody
    public JsonDocument getCurrentUser(@RequestParam String code) throws ServiceException
    {
    	User user = SessionContext.getCurrentUser();
        return new CreditAPIResult(user);
    }

    /**
     * 完善材料 第一步.
     * 
     * @return
     * @throws ServiceException
     */
    @RequestMapping(method = RequestMethod.POST, value = "/stuff/step1")
    @ResponseBody
    public JsonDocument submitStuffStep1(@RequestBody User userStuff1) throws ServiceException
    {
    	User user = SessionContext.getCurrentUser();
    	
    	// set submit info into user.
    	user.setRealName(userStuff1.getRealName());
    	user.setAddress(userStuff1.getAddress());;
    	user.setMaritalStatus(userStuff1.getMaritalStatus());
    	user.setDegree(userStuff1.getDegree());
    	
    	userService.submitStuffStep1(user);
    	
        return SUCCESS;
    }
    
    /**
     * 完善材料 第二步.
     * 
     * @return
     * @throws ServiceException
     */
    @RequestMapping(method = RequestMethod.POST, value = "/stuff/step2")
    @ResponseBody
    public JsonDocument submitStuffStep2(@RequestBody User userStuff2) throws ServiceException
    {
    	User user = SessionContext.getCurrentUser();
    	
    	// set submit info into user.
    	user.setRelativeName1(userStuff2.getRelativeName1());
    	user.setRelativeRelation1(userStuff2.getRelativeRelation1());
    	user.setRelativePhone1(userStuff2.getRelativePhone1());
    	
    	user.setRelativeName2(userStuff2.getRelativeName2());
    	user.setRelativeRelation2(userStuff2.getRelativeRelation2());
    	user.setRelativePhone2(userStuff2.getRelativePhone2());
    	
    	userService.submitStuffStep2(user);
    	
    	return SUCCESS;
    }
    
    /**
     * 完善材料 第三步.
     * 
     * @return
     * @throws ServiceException
     */
    @RequestMapping(method = RequestMethod.POST, value = "/stuff/step3")
    @ResponseBody
    public JsonDocument submitStuffStep3(@RequestBody User userStuff3) throws ServiceException
    {
    	User user = SessionContext.getCurrentUser();
    	
    	// set submit info into user.
    	user.setPhone(userStuff3.getPhone());
    	user.setPhonePassword(userStuff3.getPhone());
    	
    	userService.submitStuffStep3(user);
    	
    	return SUCCESS;
    }
    
    /**
     * 完善材料 第四步.
     * 
     * @return
     * @throws ServiceException
     */
    @RequestMapping(method = RequestMethod.POST, value = "/stuff/step4")
    @ResponseBody
    public JsonDocument submitStuffStep4(@RequestBody User userStuff4) throws ServiceException
    {
    	User user = SessionContext.getCurrentUser();
    	
    	// set submit info into user.
    	
    	
    	userService.submitStuffStep4(user);
    	
    	return SUCCESS;
    }
    
    /**
     * 设置授信额度.
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
     * 设置授信额度.
     * 
     * @return
     * @throws ServiceException
     */
    @RequestMapping(method = RequestMethod.POST, value = "/change/facility")
    @ResponseBody
    public JsonDocument changeFacility(@RequestParam String code) throws ServiceException
    {
    	
    	return SUCCESS;
    }
    
}

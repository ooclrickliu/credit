/**
 * UsersController.java
 * 
 * Copyright@2015 OVT Inc. All rights reserved. 
 * 
 * May 4, 2015
 */
package cn.wisdom.api.controller;

import me.chanjar.weixin.mp.bean.result.WxMpUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wisdom.api.response.CreditAPIResult;
import cn.wisdom.common.model.JsonDocument;
import cn.wisdom.dao.vo.AppProperty;
import cn.wisdom.service.UserService;
import cn.wisdom.service.exception.ServiceException;
import cn.wisdom.service.wx.WXService;

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
    
    @Autowired
    private WXService wxService;

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
    	WxMpUser wxMpUser = wxService.getWxMpUserByOauthCode(code);
        return new CreditAPIResult(wxMpUser);
    }

    /**
     * 完善材料 第一步.
     * 
     * @return
     * @throws ServiceException
     */
    @RequestMapping(method = RequestMethod.POST, value = "/stuff/step1")
    @ResponseBody
    public JsonDocument submitStuffStep1(@RequestParam String code) throws ServiceException
    {
    	
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
    public JsonDocument submitStuffStep2(@RequestParam String code) throws ServiceException
    {
    	
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
    public JsonDocument submitStuffStep3(@RequestParam String code) throws ServiceException
    {
    	
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
    public JsonDocument submitStuffStep4(@RequestParam String code) throws ServiceException
    {
    	
    	return SUCCESS;
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

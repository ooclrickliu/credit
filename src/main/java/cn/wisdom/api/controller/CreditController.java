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
@RequestMapping("/credit")
public class CreditController
{
    @Autowired
    private UserService userService;
    
    @Autowired
    private WXService wxService;

    private static final JsonDocument SUCCESS = CreditAPIResult.SUCCESS;
    
    @Autowired
    private AppProperty appProperties;

    /**
     * 申请借款
     * 
     * @return
     * @throws ServiceException
     */
    @RequestMapping(method = RequestMethod.POST, value = "/apply")
    @ResponseBody
    public JsonDocument applyCredit(@RequestParam String code) throws ServiceException
    {
    	WxMpUser wxMpUser = wxService.getWxMpUserByOauthCode(code);
        return new CreditAPIResult(wxMpUser);
    }
    
    /**
     * 审批通过
     * 
     * @return
     * @throws ServiceException
     */
    @RequestMapping(method = RequestMethod.POST, value = "/apply/approve")
    @ResponseBody
    public JsonDocument approveApply(@RequestParam String code) throws ServiceException
    {
    	
    	return SUCCESS;
    }
    
    /**
     * 拒绝借款申请
     * 
     * @return
     * @throws ServiceException
     */
    @RequestMapping(method = RequestMethod.POST, value = "/apply/reject")
    @ResponseBody
    public JsonDocument rejectApply(@RequestParam String code) throws ServiceException
    {
    	
    	return SUCCESS;
    }
    
    /**
     * 查询借款申请
     * 
     * @return
     * @throws ServiceException
     */
    @RequestMapping(method = RequestMethod.GET, value = "/apply/list")
    @ResponseBody
    public JsonDocument listApply(@RequestParam String code) throws ServiceException
    {
    	
    	return SUCCESS;
    }

    /**
     * 还款
     * 
     * @return
     * @throws ServiceException
     */
    @RequestMapping(method = RequestMethod.POST, value = "/return")
    @ResponseBody
    public JsonDocument returnCredit(@RequestParam String code) throws ServiceException
    {
    	
        return SUCCESS;
    }
    
    /**
     * 确认还款
     * 
     * @return
     * @throws ServiceException
     */
    @RequestMapping(method = RequestMethod.POST, value = "/return/confirm")
    @ResponseBody
    public JsonDocument confirmReturn(@RequestParam String code) throws ServiceException
    {
    	
    	return SUCCESS;
    }
    
    /**
     * 确认还款失败
     * 
     * @return
     * @throws ServiceException
     */
    @RequestMapping(method = RequestMethod.POST, value = "/return/fail")
    @ResponseBody
    public JsonDocument returnFail(@RequestParam String code) throws ServiceException
    {
    	
    	return SUCCESS;
    }
}

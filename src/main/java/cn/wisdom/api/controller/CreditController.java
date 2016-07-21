/**
 * UsersController.java
 * 
 * Copyright@2015 OVT Inc. All rights reserved. 
 * 
 * May 4, 2015
 */
package cn.wisdom.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.wisdom.api.request.CreditRequest;
import cn.wisdom.api.response.CreditAPIResult;
import cn.wisdom.common.model.JsonDocument;
import cn.wisdom.dao.vo.CreditApply;
import cn.wisdom.service.CreditService;
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
@RequestMapping("/credit")
public class CreditController
{
    @Autowired
    private CreditService creditService;

    private static final JsonDocument SUCCESS = CreditAPIResult.SUCCESS;

    /**
     * 账户概况
     * 
     * @return
     * @throws ServiceException
     */
    @RequestMapping(method = RequestMethod.GET, value = "/account")
    @ResponseBody
    public JsonDocument getAccountProfile() throws ServiceException
    {
//    	creditService.getAccountProfile(SessionContext.getCurrentUser());
    	
        return SUCCESS;
    }
    
    /**
     * 申请借款：提交借款申请
     * 
     * @return
     * @throws ServiceException
     */
    @RequestMapping(method = RequestMethod.POST, value = "/apply/step1")
    @ResponseBody
    public JsonDocument applyCreditStep1(@RequestBody CreditRequest creditRequest) throws ServiceException
    {
    	CreditApply creditApply = new CreditApply();
    	creditApply.setUserId(SessionContext.getCurrentUser().getId());
    	creditApply.setAmount(creditRequest.getAmount());
    	creditApply.setMonth(creditRequest.getMonth());
    	
    	creditApply = creditService.applyCreditStep1(creditApply);
    	
    	return SUCCESS;
    }
    
    /**
     * 申请借款: 提交手续费支付凭证
     * 
     * @return
     * @throws ServiceException
     */
    @RequestMapping(method = RequestMethod.POST, value = "/apply/step2")
    @ResponseBody
    public JsonDocument applyCreditStep2(@RequestParam long id, @RequestPart("commissionImg") MultipartFile commissionImg) throws ServiceException
    {
    	CreditApply creditApply = new CreditApply();
    	creditApply.setId(id);
    	creditApply.setCommissionImg(commissionImg);
    	
    	creditService.applyCreditStep2(creditApply);
    	
    	return SUCCESS;
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

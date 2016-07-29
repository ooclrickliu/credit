/**
 * UsersController.java
 * 
 * Copyright@2015 OVT Inc. All rights reserved. 
 * 
 * May 4, 2015
 */
package cn.wisdom.api.controller.admin;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wisdom.api.response.CreditAPIResult;
import cn.wisdom.api.response.CreditApplyResponse;
import cn.wisdom.api.response.ResponseBuilder;
import cn.wisdom.common.model.JsonDocument;
import cn.wisdom.dao.constant.ApplyState;
import cn.wisdom.dao.vo.CreditApply;
import cn.wisdom.dao.vo.CreditPayRecord;
import cn.wisdom.dao.vo.User;
import cn.wisdom.service.CreditService;
import cn.wisdom.service.UserService;
import cn.wisdom.service.exception.ServiceException;

/**
 * CreditController
 * 
 * @Author zhi.liu
 * @Version 1.0
 * @See
 * @Since [Credit]/[API] 1.0
 */

@Controller
@RequestMapping("/admin/credit")
public class CreditAdminController {
	
	@Autowired
	private CreditService creditService;
	
	@Autowired
	private UserService userService;

	private static final JsonDocument SUCCESS = CreditAPIResult.SUCCESS;

	/**
	 * 借款申请列表
	 * 
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/apply/list")
	@ResponseBody
	public JsonDocument getApplyList(@RequestParam String state)
			throws ServiceException {
		List<ApplyState> states = new ArrayList<ApplyState>();
		states.add(ApplyState.valueOf(state));
		List<CreditApply> applyList = creditService.getApplyList(-1, states, null, true);
		
		List<Long> userIdList = new ArrayList<Long>();
		for (CreditApply creditApply : applyList) {
			userIdList.add(creditApply.getUserId());
		}
		List<User> userList = userService.getUserList(userIdList);
		
		List<CreditApplyResponse> response = ResponseBuilder.buildCreditApplyResposne(applyList, userList);

		return new CreditAPIResult(response);
	}
	
	/**
	 * 借款单对应的还款记录列表：Approved, Overdue
	 * 
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/apply/detail")
	@ResponseBody
	public JsonDocument getApplyPayRecordDetail(@RequestParam String applyId)
			throws ServiceException {
		
		CreditApplyResponse response = new CreditApplyResponse();
		
		response.setCreditApply(creditService.getCreditApply(applyId));
		
		List<CreditPayRecord> applyList =  creditService.getApplyPayRecords(applyId);
		response.setPayRecords(applyList);
		
		return new CreditAPIResult(response);
	}
	
	/**
	 * 还款申请以及用户信息
	 * 
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/apply/detail2")
	@ResponseBody
	public JsonDocument getApplyOfReturn(@RequestParam String applyId)
			throws ServiceException {
		
		CreditApply creditApply = creditService.getCreditApply(applyId);
		
		CreditApplyResponse creditApplyResponse = new CreditApplyResponse();
		creditApplyResponse.setCreditApply(creditApply);
		creditApplyResponse.setUser(userService.getUserById(creditApply.getUserId()));
		
		return new CreditAPIResult(creditApplyResponse);
	}
	
	/**
	 * 借款审批通过
	 * 
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/apply/approve")
	@ResponseBody
	public JsonDocument approveApply(@RequestParam long applyId)
			throws ServiceException {
		creditService.approve(applyId);
		
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
	public JsonDocument rejectApply(@RequestParam long applyId, @RequestParam String note)
			throws ServiceException {
		creditService.reject(applyId, note);
		return SUCCESS;
	}

	/**
	 * 还款申请列表
	 * 
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/return/list")
	@ResponseBody
	public JsonDocument getReturnList(@RequestParam String state)
			throws ServiceException {

		List<CreditPayRecord> applyPayRecords = creditService.getApplyPayRecords(ApplyState.valueOf(state));
		
		return new CreditAPIResult(applyPayRecords);
	}

	/**
	 * 确认还款
	 * 
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/return/confirm")
	@ResponseBody
	public JsonDocument confirmReturn(@RequestParam long payRecordId, float returnAmount)
			throws ServiceException {
		
		creditService.confirmReturn(payRecordId, returnAmount);
		
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
	public JsonDocument returnFail(@RequestParam long payRecordId)
			throws ServiceException {

		creditService.returnFail(payRecordId);
		
		return SUCCESS;
	}
}

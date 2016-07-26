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
import cn.wisdom.common.model.JsonDocument;
import cn.wisdom.dao.constant.ApplyState;
import cn.wisdom.dao.vo.CreditApply;
import cn.wisdom.service.CreditService;
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

	private static final JsonDocument SUCCESS = CreditAPIResult.SUCCESS;

	/**
	 * 审批通过
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

		return new CreditAPIResult(applyList);
	}
	
	/**
	 * 审批通过
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
	 * 确认还款
	 * 
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/return/list")
	@ResponseBody
	public JsonDocument confirmReturn(@RequestParam String state)
			throws ServiceException {

		creditService.getApplyPayRecords(ApplyState.valueOf(state));
		
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

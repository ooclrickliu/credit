/**
 * UsersController.java
 * 
 * Copyright@2015 OVT Inc. All rights reserved. 
 * 
 * May 4, 2015
 */
package cn.wisdom.api.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wisdom.api.response.CreditAPIResult;
import cn.wisdom.common.model.JsonDocument;
import cn.wisdom.common.utils.CookieUtil;
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
@RequestMapping("/admin/users")
public class UsersAdminController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AppProperty appProperty;

	private static final JsonDocument SUCCESS = CreditAPIResult.SUCCESS;

	/**
	 * Get current User.
	 * 
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/current")
	@ResponseBody
	public JsonDocument getCurrentUser() throws ServiceException {
		User user = SessionContext.getCurrentUser();
		return new CreditAPIResult(user);
	}

	/**
	 * login.
	 * 
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/login")
	@ResponseBody
	public JsonDocument login(HttpServletResponse response,
			@RequestParam String phone, @RequestParam String password)
			throws ServiceException {
		
		String accessToken = userService.login(phone, password);

		CookieUtil.addCookie(response, CookieUtil.KEY_ACCESS_TOKEN,
				accessToken, appProperty.cookieAccessTokenHourAge * CookieUtil.ONE_HOUR);

		return SUCCESS;
	}
	
    /**
     * logout.
     * 
     * @param response
     * @return
     * @throws ServiceException
     */
    @RequestMapping(method = RequestMethod.POST, value = "/logout")
    @ResponseBody
    public JsonDocument logout(HttpServletRequest request,
            HttpServletResponse response) throws ServiceException
    {
        String accessToken =
                CookieUtil.getCookie(request, CookieUtil.KEY_ACCESS_TOKEN);
        userService.logout(accessToken);

        CookieUtil.addCookie(response, CookieUtil.KEY_ACCESS_TOKEN,
                accessToken, 0);

        return SUCCESS;
    }

    /**
     * Change user password.
     * 
     * @param oldPassword
     * @param newPassword
     * @return
     * @throws ServiceException
     */
    @RequestMapping(method = RequestMethod.POST, value = "/changePassword")
    @ResponseBody
    public JsonDocument changePassword(HttpServletResponse response,
            @RequestParam String oldPassword, @RequestParam String newPassword)
            throws ServiceException
    {
        String newAccessToken =
                userService.changePassword(oldPassword, newPassword);

        CookieUtil.addCookie(response, CookieUtil.KEY_ACCESS_TOKEN,
                newAccessToken, appProperty.cookieAccessTokenHourAge);

        return SUCCESS;
    }

	/**
	 * 根据状态查询用户列表.
	 * 
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/list")
	@ResponseBody
	public JsonDocument getUserList(@RequestParam String state)
			throws ServiceException {
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
	@RequestMapping(method = RequestMethod.POST, value = "/approve")
	@ResponseBody
	public JsonDocument approve(@RequestBody User user) throws ServiceException {
		User selectedUser = userService.getUserById(user.getId());

		selectedUser.setUserState(UserState.Approved);
		selectedUser.setLevel(user.getLevel());
		selectedUser.setApproveNote(user.getApproveNote());

		userService.approve(selectedUser);

		return new CreditAPIResult(selectedUser);
	}

}

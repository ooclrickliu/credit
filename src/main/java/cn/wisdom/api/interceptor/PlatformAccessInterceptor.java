/**
 * PlatformAccessInterceptor.java
 * 
 * Copyright@2015 OVT Inc. All rights reserved. 
 * 
 * May 4, 2015
 */
package cn.wisdom.api.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.wisdom.api.exception.NoAccessException;
import cn.wisdom.api.response.CreditAPIResult;
import cn.wisdom.common.model.JsonDocument;
import cn.wisdom.common.utils.HttpUtils;
import cn.wisdom.common.utils.StringUtils;
import cn.wisdom.dao.vo.User;
import cn.wisdom.service.UserService;
import cn.wisdom.service.context.SessionContext;
import cn.wisdom.service.exception.ServiceErrorCode;
import cn.wisdom.service.exception.ServiceException;

/**
 * PlatformAccessInterceptor do the following things: <li>identify user by
 * access token from cookies before call controller</li>
 * 
 * @Author zhi.liu
 * @Version 1.0
 * @See
 * @Since [OVT Cloud Platform]/[API] 1.0
 */
public class PlatformAccessInterceptor extends HandlerInterceptorAdapter
{
    private static final String OAUTH_CODE = "code";
    
    private static final String OPENID = "openid";

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) throws Exception
    {
        super.preHandle(request, response, handler);
        
        User user = null;
        String openId = HttpUtils.getParamValue(request, OPENID);
        if (StringUtils.isNotBlank(openId)) {
        	user = userService.getUserByOpenId(openId);
        	
        	if (user == null) {
                String code = HttpUtils.getParamValue(request, OAUTH_CODE);
                if (StringUtils.isNotBlank(code)) {
                	user = userService.getUserByOauthCode(code);
				}
			}
		}
        
        if (user == null) {
        	writeResponse(response, ServiceErrorCode.NOT_SUBSCRIB);
		}
        else {
        	initSessionContext(user);
		}

        return true;
    }

    /**
     * Initial session context.
     * 
     * @param user
     * @throws ServiceException
     * @throws NoAccessException
     */
    private void initSessionContext(User user)
    {
        SessionContext.setCurrentUser(user);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler, ModelAndView modelAndView) throws Exception
    {
        super.postHandle(request, response, handler, modelAndView);
        SessionContext.destroy();
    }

    private void writeResponse(HttpServletResponse response, String errCode) throws Exception
    {
        JsonDocument respBody = new CreditAPIResult(errCode);

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.write(respBody, MediaType.APPLICATION_JSON, new ServletServerHttpResponse(
                response));
    }
}

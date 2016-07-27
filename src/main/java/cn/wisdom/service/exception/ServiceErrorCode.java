/**
 * PlatformErrorCode.java
 * 
 * Copyright@2015 OVT Inc. All rights reserved. 
 * 
 * May 5, 2015
 */
package cn.wisdom.service.exception;

/**
 * PlatformErrorCode
 * 
 * @Author zhi.liu
 * @Version 1.0
 * @See 
 * @Since [OVT Cloud Platform]/[API] 1.0
 */
public class ServiceErrorCode
{
    //================App===================//
    //system
    public static final String SYSTEM_UNEXPECTED = "SystemUnexpected";
    
    //user
    public static final String NOT_SUBSCRIB = "NotSubscribe";
    public static final String NOT_LOGIN = "NotLogin";
    public static final String NO_AVAILABLE_CREDIT_LINE = "NoAvailableCreditLine";
    
    //WX
    public static final String OAUTH_FAIL = "OAuthFail";
    
    //================Console===================//
    public static final String INVALID_ACCESS_TOKEN = "InvalidAccessToken";
	public static final String USER_NOT_EXIST = "UserNotExist";
	public static final String WRONG_PASSWORD = "WrongPassword";
	public static final String SAME_PASSWORD = "SamePassword";
	public static final String INVALID_PASSWORD_FORMAT = "InvalidPasswordFormat";
    
}

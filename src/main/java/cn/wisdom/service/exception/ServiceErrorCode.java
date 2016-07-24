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
    
    //system
    public static final String SYSTEM_UNEXPECTED = "SystemUnexpected";
    
    //user
    public static final String NOT_SUBSCRIB = "NotSubscribe";
    public static final String NO_AVAILABLE_CREDIT_LINE = "NoAvailableCreditLine";
//    public static final String NO_PERMISSION = "NoPermission";
//    public static final String INVALID_ACCESS = "InvalidAccess";
//    //public static final String INVALID_DATA_FORMAT = "InvalidDataFormat";
//    public static final String DUPLICATE_USER_NAME = "UserNameIsExist";
//    public static final String WRONG_PASSWORD = "WrongPassword";
//    public static final String INVALID_PASSWORD = "InvalidPassword";
//    public static final String USER_NOT_EXIST = "UserNotExist";
//    public static final String GET_USER_FAILED = "GetUserFailed";
//    public static final String CHECK_NAME_FAILED = "CheckUserNameFailed";
//    public static final String CHANGE_PASSWORD_FAILED = "ChangePasswordFailed";
//    public static final String LIST_USER_FAILED = "ListUserFailed";
//    public static final String CREATE_USER_FAILED = "CreateUserFailed";
//    public static final String DELETE_USER_FAILED = "DeleteUserFailed";
//    public static final String CAN_NOT_DELETE_SA = "CanNotDeleteSA";
//    public static final String SAME_NEW_OLD_PASSWORD = "NewAndOldPasswordIsSame";
    
    //WX
    public static final String OAUTH_FAIL = "OAuthFail";
    
}

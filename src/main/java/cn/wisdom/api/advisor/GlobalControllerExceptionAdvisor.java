/**
 * GlobalExceptionController.java
 * 
 * Copyright@2015 OVT Inc. All rights reserved. 
 * 
 * Jun 17, 2015
 */
package cn.wisdom.api.advisor;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wisdom.api.response.CreditAPIResult;
import cn.wisdom.common.log.Logger;
import cn.wisdom.common.log.LoggerFactory;
import cn.wisdom.common.model.JsonDocument;
import cn.wisdom.dao.constant.LoggerConstants;
import cn.wisdom.service.exception.ServiceErrorCode;
import cn.wisdom.service.exception.ServiceException;

/**
 * GlobalExceptionController
 * 
 * @Author zhi.liu
 * @Version 1.0
 * @See
 * @Since [OVT Cloud Platform]/[API] 1.0
 */
@ControllerAdvice
public class GlobalControllerExceptionAdvisor
{
    private static final Logger LOGGER = LoggerFactory
            .getLogger(LoggerConstants.SYSTEM_LOGGER);

    @ExceptionHandler
    @ResponseBody
    public JsonDocument handleServiceException(ServiceException serviceException)
    {
        LOGGER.error("Controller catches service exception!", serviceException);
        return new CreditAPIResult(serviceException.getErrorCode());
    }

    @ExceptionHandler
    @ResponseBody
    public JsonDocument handleRuntimeException(RuntimeException runtimeException)
    {
        LOGGER.error("Controller catches runtime exception!", runtimeException);
        return new CreditAPIResult(ServiceErrorCode.SYSTEM_UNEXPECTED);
    }
}

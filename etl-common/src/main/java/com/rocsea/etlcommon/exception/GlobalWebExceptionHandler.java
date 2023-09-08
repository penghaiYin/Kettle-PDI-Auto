package com.rocsea.etlcommon.exception;

import com.rocsea.etlcommon.model.response.ResponseWrapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * 异常拦截器
 *
 * @author rocsea
 * @Date 2022/12/7
 */
@ControllerAdvice
public class GlobalWebExceptionHandler {

    @Resource
    private HttpServletResponse response;

    private static final String INTERNAL_SERVER_ERROR = "内部错误:";

    private static final String BUSINESS_ERROR = "业务错误|";

    private final Log logger = LogFactory.getLog(this.getClass());
    private static final String EXCEPTION_HEADER = "exception:";

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    ResponseWrapper handleBusinessException(BusinessException e) {
        logger.error(EXCEPTION_HEADER, e);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        String errorMessage =  BUSINESS_ERROR + e.getMessage();
        return ResponseWrapper.fail(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, errorMessage);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    ResponseWrapper handleException(Exception e) {
        logger.error(EXCEPTION_HEADER, e);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return ResponseWrapper.fail(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR);
    }

}

package com.richard.service;

import com.richard.service.constant.ErrorCode;
import com.richard.service.domain.common.CommonException;
import com.richard.service.domain.common.RestResult;
import com.richard.service.utils.ExceptionUtil;
import com.richard.service.utils.RestGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

//    /**
//     * 系统异常处理，比如：404,500
//     * @param req
//     * @param e
//     * @return
//     * @throws Exception
//     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public <T> RestResult<T> defaultErrorHandler(HttpServletRequest req, Exception e) {
        LOGGER.error(ExceptionUtil.getErrorInfo(e));
        RestResult<T> restResult;
        if (e instanceof org.springframework.web.servlet.NoHandlerFoundException) {
            restResult = RestGenerator.genErrorResult(ErrorCode.ERROR_NOT_FOUND);
        } else if(e instanceof CommonException){
            restResult =  RestGenerator.genErrorResult(e.getMessage());
        }else{
            restResult =  RestGenerator.genErrorResult(ErrorCode.ERROR_SERVER);
        }
        return restResult;
    }

//    @ExceptionHandler
//    @ResponseBody
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public  <T> RestResult<T> runtimeExceptionHandler(Exception e) {
//        LOGGER.error("---------> huge error!", e);
//        return RestGenerator.genErrorResult(ErrorCode.ERROR_SERVER);
//    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseBody
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public  <T> RestResult<T> illegalParamsExceptionHandler(MethodArgumentNotValidException e) {
//        LOGGER.error("---------> invalid request!", e);
//        return RestGenerator.genErrorResult(ErrorCode.ILLEGAL_PARAMS);
//    }
//
//    @ExceptionHandler
//    @ResponseBody
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public  <T> RestResult<T> notFoundExceptionHandler(NoHandlerFoundException e) {
//        LOGGER.error("---------> invalid request!", e);
//        return RestGenerator.genErrorResult(ErrorCode.ERROR_NOT_FOUND);
//    }
}

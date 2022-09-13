package com.zhihao.newretail.core.exception;

import com.zhihao.newretail.core.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/*
 * 全局异常处理
 * */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /*
    * 业务异常、自定义异常
    * */
    @ExceptionHandler(ServiceException.class)
    public R handlerServiceException(ServiceException e) {
        Integer code = e.getCode();
        String msg = e.getMsg();
        log.error(e.getMsg(), e);
        return R.error(code, msg);
    }

    /*
    * Validation 异常
    * */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R handlerMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String message = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
        log.error("请求地址'{}',表单校验异常'{}'.", requestURI, message);
        return R.error(message);
    }

    /*
    * 运行时未知异常
    * */
    @ExceptionHandler(RuntimeException.class)
    public R handlerRuntimeException(RuntimeException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',后端执行异常.", requestURI, e);
        return R.error(e.getMessage());
    }

    /*
    * 系统异常
    * */
    @ExceptionHandler(Exception.class)
    public R handlerException(Exception e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',后端系统异常.", requestURI, e);
        return R.error(e.getMessage());
    }

}

package com.boot.framework.controller;

import com.boot.framework.exception.CodeException;
import com.boot.framework.util.ResultCode;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: lindj
 * @date: 2018/4/12 14:31
 * @description: 处理未到达controller层的异常
 */
@RestController
public class HttpErrorController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    /**
     * 处理未到达controller层的异常，直接抛出自定义异常
     *
     * @return Object
     */
    @RequestMapping(value = ERROR_PATH)
    public Object handleError(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Integer value = HttpStatus.METHOD_NOT_ALLOWED.value();
        if (value.equals(statusCode)) {
            throw new CodeException(ResultCode.Codes.NOT_ALLOWED);
        }
        throw new CodeException(ResultCode.Codes.NOT_FIND);
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}

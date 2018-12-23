package com.zjdex.framework.controller;

import com.zjdex.framework.exception.CodeException;
import com.zjdex.framework.util.ResultCode;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: lindj
 * @date: 2018/4/12 14:31
 * @description: 处理未到达controller层的异常
 */
//@RestController
public class HttpErrorController implements ErrorController {

    private static final String ERROR_PATH = "/error";
    /**
     * 处理未到达controller层的异常，直接抛出自定义异常
     *
     * @return Object
     */
    @RequestMapping(value = ERROR_PATH)
    public Object handleError() {
        throw new CodeException(ResultCode.Codes.NOT_FIND);
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}

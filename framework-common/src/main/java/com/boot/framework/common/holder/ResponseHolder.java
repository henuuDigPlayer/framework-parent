package com.boot.framework.common.holder;

import com.boot.framework.common.bean.BaseResponse;
import com.boot.framework.common.enums.CodeEnum;
import com.boot.framework.common.util.data.JsonUtil;
import com.boot.framework.common.util.ResponseUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author: lindj
 * @date: 2018/11/2
 * @description: 获取
 */
public class ResponseHolder {

    /**
     * 获取HttpServletResponse
     *
     * @return HttpServletResponse
     */
    public static HttpServletResponse getResponse() {
        HttpServletResponse response =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        return response;
    }

    public static void writeResponse(HttpServletResponse response, CodeEnum codeEnum) throws IOException {
        BaseResponse baseResp = ResponseUtil.error(codeEnum);
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(JsonUtil.objectToJson(baseResp));
        out.flush();
        out.close();
    }

    public static void writeResponse(CodeEnum codeEnum) throws IOException {
        HttpServletResponse response = getResponse();
        BaseResponse baseResp = ResponseUtil.error(codeEnum);
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(JsonUtil.objectToJson(baseResp));
        out.flush();
        out.close();
    }

    public static void writeResponse(HttpServletResponse response, Integer code, String message) throws IOException {
        BaseResponse baseResp = ResponseUtil.error(code, message);
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(JsonUtil.objectToJson(baseResp));
        out.flush();
        out.close();
    }

    public static void writeResponse(HttpServletResponse response, CodeEnum codeEnum, int status) throws IOException {
        BaseResponse baseResp = ResponseUtil.error(codeEnum);
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(status);
        PrintWriter out = response.getWriter();
        out.print(JsonUtil.objectToJson(baseResp));
        out.flush();
        out.close();
    }

}

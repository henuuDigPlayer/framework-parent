package com.zjdex.framework.holder;

import com.zjdex.framework.bean.BaseResponse;
import com.zjdex.framework.enums.CodeEnum;
import com.zjdex.framework.util.data.JsonUtil;
import com.zjdex.framework.util.ResponseUtil;
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

    public static void writeResponse(HttpServletResponse response, Object baseResponse,
                                     int status) throws IOException {
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        if(status > 0) {
            response.setStatus(status);
        }
        PrintWriter out = response.getWriter();
        out.print(JsonUtil.objectToJson(baseResponse));
        out.flush();
        out.close();
    }

}

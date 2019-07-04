package com.zjdex.framework.filter;

import com.zjdex.framework.holder.ResponseHolder;
import com.zjdex.framework.util.ResponseUtil;
import com.zjdex.framework.util.constant.ConstantUtil;
import com.zjdex.framework.util.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author lindj
 * @date 2018/6/15 16:04
 * @description 缓存请求参数
 */
public class HttpCacheRequestFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(HttpCacheRequestFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 解决跨域问题
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, " +
                "If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        request.setAttribute("begin", System.currentTimeMillis());

        try {
            boolean value = ConstantUtil.METHOD_POST.equals(request.getMethod()) &&
                    !request.getContentType().contains(ConstantUtil.FILE_CONTENT_TYPE);
            if (value) {
                MyHttpServletRequestWrapper requestWrapper = new MyHttpServletRequestWrapper(request);
                filterChain.doFilter(requestWrapper, response);
            } else {
                filterChain.doFilter(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ResponseHolder.writeResponse(response,
                    ResponseUtil.error(ResultCode.Codes.BUSINESS_ERROR), 0);
        }

    }


    @Override
    public void destroy() {

    }

    public static class MyHttpServletRequestWrapper extends HttpServletRequestWrapper {

        private final byte[] body;

        public MyHttpServletRequestWrapper(HttpServletRequest request)
                throws Exception {
            super(request);
            try (BufferedInputStream bis = new BufferedInputStream(request.getInputStream());
                 ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                byte[] buffer = new byte[1024 * 5];
                int len;
                while ((len = bis.read(buffer)) > 0) {
                    baos.write(buffer, 0, len);
                }
                body = baos.toByteArray();
            } catch (IOException ex) {
                throw ex;
            }
        }

        @Override
        public BufferedReader getReader() throws IOException {
            return new BufferedReader(new InputStreamReader(getInputStream()));
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            final ByteArrayInputStream bais = new ByteArrayInputStream(body);
            return new ServletInputStream() {
                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setReadListener(ReadListener readListener) {
                }

                @Override
                public int read() throws IOException {
                    return bais.read();
                }

            };
        }
    }

}

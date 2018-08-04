package com.zjdex.framework.filter;

import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.FilterChain;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: lindj
 * @date: 2018/6/15 16:04
 * @description: 缓存请求参数
 */
@WebFilter(
        filterName="httpCacheRequestFilter",
        urlPatterns={"/*"})
public class HttpCacheRequestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ContentCachingRequestWrapper request;
        if (servletRequest instanceof ContentCachingRequestWrapper) {
            request = (ContentCachingRequestWrapper) servletRequest;
        }
        else {
            request = new ContentCachingRequestWrapper((HttpServletRequest)servletRequest);
        }

        ContentCachingResponseWrapper response;
        if (servletResponse instanceof ContentCachingResponseWrapper) {
            response = (ContentCachingResponseWrapper) servletResponse;
        }
        else {
            response = new ContentCachingResponseWrapper((HttpServletResponse)servletResponse);
        }

        doFilter(request, response, filterChain);
    }

    @Override
    public void destroy() {

    }
}

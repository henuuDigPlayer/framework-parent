package com.zjdex.framework.config;

import com.zjdex.framework.filter.HttpCacheRequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: lindj
 * @date: 2018/12/3
 * @description:
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean setCacheFilter(){
        FilterRegistrationBean filterBean = new FilterRegistrationBean();
        filterBean.setFilter(new HttpCacheRequestFilter());
        filterBean.setName("HttpCacheRequestFilter");
        filterBean.addUrlPatterns("/*");
        filterBean.setOrder(1);
        return filterBean;
    }

}

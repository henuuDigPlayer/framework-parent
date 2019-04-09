package com.zjdex.framework.config;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

/**
 * @author lindj
 * @date 2018/4/12 12:30
 * @description bean定义
 */
@Configuration
public class HttpMessageConverterConfig {

    /**
     * fastJson 接入，重写httpMessageConvert
     *
     * @return HttpMessageConverters
     */
    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters( FastJsonConfig fastJsonConfig) {
        // 定义一个converter转换消息的对象
        FastJsonHttpMessageConverter fasHttpMessageConverter = new FastJsonHttpMessageConverter();
        // 在converter中添加配置信息
        fasHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        HttpMessageConverter<?> converter = fasHttpMessageConverter;
        return new HttpMessageConverters(converter);
    }

}

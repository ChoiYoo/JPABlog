package com.example.jpablog.config;

import com.example.jpablog.common.interceptor.CommonInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguartion implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new CommonInterceptor())
                .addPathPatterns("/api/*")
                .excludePathPatterns("/api/puublic/*");

    }
}

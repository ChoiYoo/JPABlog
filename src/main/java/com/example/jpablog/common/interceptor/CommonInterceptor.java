package com.example.jpablog.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class CommonInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("#########################################");
        log.info("[인터셉터] - preHandler 스타트");
        log.info("#########################################");

        log.info(request.getMethod());
        log.info(request.getRequestURI());
        return true;
    }
}

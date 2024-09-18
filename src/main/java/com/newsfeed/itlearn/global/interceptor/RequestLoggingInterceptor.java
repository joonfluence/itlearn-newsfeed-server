package com.newsfeed.itlearn.global.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class RequestLoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String method = request.getMethod();
        String uri = request.getRequestURI();

        if (method.equals("GET") || method.equals("DELETE")) {
            log.info("Request Method: {}, Url: {}, Params: {}", method, uri, request.getQueryString());
        }

        return true;
    }
}


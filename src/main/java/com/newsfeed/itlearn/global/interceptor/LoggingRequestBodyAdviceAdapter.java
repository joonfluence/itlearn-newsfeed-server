package com.newsfeed.itlearn.global.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Type;

@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class LoggingRequestBodyAdviceAdapter extends RequestBodyAdviceAdapter {

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(final HttpInputMessage inputMessage,
                                           final MethodParameter parameter,
                                           final Type targetType, final Class<? extends HttpMessageConverter<?>> converterType) {
        return inputMessage;
    }

    @Override
    public Object afterBodyRead(final Object body, final HttpInputMessage httpInputMessage,
                                final MethodParameter parameter,
                                final Type targetType, final Class<? extends HttpMessageConverter<?>> converterType) {

        // 요청의 본문을 읽기 전에 로그를 기록
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String path = request.getServletPath();
        String httpMethod = request.getMethod();

        ObjectMapper mapper = new ObjectMapper();
        String requestBody = "";

        try {
            requestBody = mapper.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            requestBody = "";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        log.info("[Request Log] Method: {}, Path: {}, UserId: {}, Body: {}", httpMethod, path, userId, requestBody);
        return body;
    }
}
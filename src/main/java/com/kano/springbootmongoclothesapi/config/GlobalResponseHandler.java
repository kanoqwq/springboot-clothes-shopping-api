package com.kano.springbootmongoclothesapi.config;

import com.kano.springbootmongoclothesapi.common.ApiResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.function.DoubleToIntFunction;

@ControllerAdvice
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        // 拦截所有返回结果，排除 ApiResponse 类型的返回值，避免重复包装
        return !returnType.getParameterType().equals(ApiResponse.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {

        // 这里可以获取并使用响应码
        // 获取 HttpStatus，如果没有设置，默认为 200
        HttpStatus status = HttpStatus.OK;
        int code = status != null ? status.value() : 200;

        // 避免重复包装
        if (body instanceof ApiResponse) {
            return body;
        }

        // 包装返回值
        return new ApiResponse<>(code, status != null ? status.getReasonPhrase() : "OK", body);
    }
}
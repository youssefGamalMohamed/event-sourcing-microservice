package com.youssef.gamal.ecommerce.microservice.category.common.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    private final ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        String declaringClassName = returnType.getDeclaringClass().getName();
        if (declaringClassName.contains("springdoc")
                || declaringClassName.contains("swagger")
                || declaringClassName.contains("actuate")
                || declaringClassName.contains("actuator")) {
            return false;
        }

        Class<?> paramType = returnType.getParameterType();
        if (byte[].class.isAssignableFrom(paramType) || Resource.class.isAssignableFrom(paramType)) {
            return false;
        }

        if (ByteArrayHttpMessageConverter.class.isAssignableFrom(converterType)
                || ResourceHttpMessageConverter.class.isAssignableFrom(converterType)) {
            return false;
        }

        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {

        if (body instanceof ApiResponse) {
            return body;
        }

        if (request != null && request.getURI() != null) {
            String path = request.getURI().getPath();
            if (path != null && (path.startsWith("/v3/api-docs") || path.startsWith("/swagger-ui") || path.startsWith("/actuator"))) {
                return body;
            }
        }

        int statusCode = 200;
        if (response instanceof ServletServerHttpResponse servletResponse) {
            statusCode = servletResponse.getServletResponse().getStatus();
        }

        String path = (request != null && request.getURI() != null) ? request.getURI().getPath() : "";
        ApiResponse<Object> apiResponse = ApiResponse.success(body, statusCode, path);

        if (body instanceof String) {
            try {
                return objectMapper.writeValueAsString(apiResponse);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error wrapping String response in ApiResponse", e);
            }
        }

        return apiResponse;
    }
}


package com.orange.common.web;

import java.lang.reflect.Type;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.orange.model.common.dto.ResponseResult;

import lombok.extern.slf4j.Slf4j;

/**
 * 统一返回结果处理
 *
 * @author Jeong Geol
 */
@Slf4j
@RestControllerAdvice(value = {"com.orange.user.controller", "com.orange.article.controller",
        "com.orange.wemedia.controller"})
public class WebApiResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter rt, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        boolean hasRestControllerAnnotation = rt.getDeclaringClass().getAnnotation(RestController.class) != null;
        boolean hasResponseBodyAnnotation = rt.getMethodAnnotation(ResponseBody.class) != null;
        boolean isJsonConverter = MappingJackson2HttpMessageConverter.class.isAssignableFrom(converterType);
        boolean isResponseEntity = ResponseEntity.class.isAssignableFrom(rt.getParameterType());
        return (hasRestControllerAnnotation || hasResponseBodyAnnotation || isResponseEntity) && isJsonConverter;
    }

    @Override
    public Object beforeBodyWrite(Object body, @NonNull MethodParameter returnType,
            @NonNull MediaType selectedContentType,
            @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType, @NonNull ServerHttpRequest request,
            @NonNull ServerHttpResponse response) {
        if (body instanceof ResponseResult) {
            return body;
        }
        Type gpt = returnType.getGenericParameterType();
        if (String.class.equals(gpt)) {
            return body;
        }
        if (Void.TYPE.equals(gpt)) {
            return ResponseResult.okResult();
        }
        /* if (body instanceof Page) {
            return ApiResult.success((Page<?>)body);
        } */
        return ResponseResult.okResult(body);
    }

}

package com.example.feigndemo.client;

import com.example.feigndemo.feign.FeignError;
import com.example.feigndemo.feign.FeignErrorTemplate;
import com.example.feigndemo.feign.MappingJackson2ErrorDecoder;
import com.example.feigndemo.feign.type.ErrorServiceType;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.io.Reader;
import java.util.Collections;

public class FooConfiguration {

    @Bean
    public ErrorDecoder lawseecomApiErrorDecoder(ObjectMapper objectMapper) {
        return new MappingJackson2ErrorDecoder<>(objectMapper, FooError.class);
    }

    @Bean
    public Decoder decoder() {
        return (response, type) -> {
            try (Reader reader = response.body().asReader()) {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(reader, FooResponse.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Bean
    public Encoder formEncoder() {
        return new feign.form.FormEncoder();
    }

    @Data
    static class FooError implements FeignErrorTemplate {

        private String result;

        private String message;
        @Override
        public FeignError getError() {
            return FeignError.of(result, ErrorServiceType.DEFAULT, message, Collections.emptyMap());
        }

    }

    @Bean
    public RequestInterceptor requestHeaderAppender() {
        return new RequestHeaderAppender();
    }

    private static class RequestHeaderAppender implements RequestInterceptor {

        private Boolean matches(String method, HttpMethod... httpMethods) {
            for (HttpMethod httpMethod : httpMethods) {
                if (httpMethod.matches(method)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public void apply(RequestTemplate template) {
            template.header(HttpHeaders.AUTHORIZATION, "auth");
        }
    }
}

package com.example.feigndemo.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

@RequiredArgsConstructor
public class MappingJackson2ErrorDecoder<T extends FeignErrorTemplate> extends AbstractClientHttpResponseErrorDecoder {

    private final ObjectMapper objectMapper;
    private final Class<T> clazz;

    @Override
    protected FeignError decode(String methodKey,
                                ClientHttpResponse response) throws IOException {
        return objectMapper.readValue(response.getBody(), clazz).getError();
    }
}

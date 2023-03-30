package com.example.feigndemo.feign;

import com.example.feigndemo.feign.type.ClientErrorType;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.Objects;

@Slf4j
public abstract class AbstractClientHttpResponseErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultDecoder = new Default();


    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            ClientHttpResponse clientHttpResponse = FeignClientHttpResponse.of(response);
            FeignError error = decode(methodKey, clientHttpResponse);
            if (Objects.isNull(error)) {
                return defaultDecoder.decode(methodKey, response);
            }

            if (clientHttpResponse.getStatusCode().is4xxClientError()) {
                //특수 오류에 대한 예외처리
                //type 을 ServiceType 이 아니라 Exception 을 그대로 넘겨준다.
                if (error.getCode().equals(ClientErrorType.FORBIDDEN_MEMBER_DORMACY.getCode())) {
                    return new IllegalArgumentException();
                } else if (error.getCode().equals(ClientErrorType.FORBIDDEN_MEMBER_UNDER_FOURTEEN.getCode())) {
                    return new IllegalArgumentException();
                } else if (error.getCode().equals(ClientErrorType.FORBIDDEN_MEMBER_NEED_CERTIFICATION.getCode())) {
                    return new IllegalArgumentException();
                }
                return new IllegalArgumentException();
            }

            return new IllegalArgumentException();
        } catch (IOException error) {
            log.error("decode of response message failed", error);
            return defaultDecoder.decode(methodKey, response);
        }
    }

    protected abstract FeignError decode(String methodKey,
                                         ClientHttpResponse response) throws IOException;

}

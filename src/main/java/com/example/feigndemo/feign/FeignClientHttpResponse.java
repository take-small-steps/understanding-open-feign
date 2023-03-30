package com.example.feigndemo.feign;

import feign.Response;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FeignClientHttpResponse implements ClientHttpResponse {

    private final Response response;
    private final HttpHeaders headers;

    @Override
    public HttpHeaders getHeaders() {
        return headers;
    }

    @Override
    public HttpStatus getStatusCode() throws IOException {
        return HttpStatus.valueOf(response.status());
    }

    @Override
    public int getRawStatusCode() throws IOException {
        return response.status();
    }

    @Override
    public String getStatusText() throws IOException {
        return response.reason();
    }

    @Override
    public InputStream getBody() throws IOException {
        return response.body().asInputStream();
    }

    @Override
    public void close() {
        // nothing
    }

    @Override
    public String toString() {
        return response.toString();
    }

    public static FeignClientHttpResponse of(Response response) {
        Objects.requireNonNull(response);

        HttpHeaders headers = new HttpHeaders();
        for (Map.Entry<String, Collection<String>> entry : response.headers().entrySet()) {
            headers.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }
        return new FeignClientHttpResponse(response, headers);
    }

}

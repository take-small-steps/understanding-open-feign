package com.example.feigndemo.feign;

public interface FeignClientResponse<T> {
    boolean isOk();
    T getData();

}

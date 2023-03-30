package com.example.feigndemo.feign;

import com.example.feigndemo.feign.type.ErrorServiceType;
import lombok.Value;

@Value(staticConstructor = "of")
public class FeignError {
    String code;
    ErrorServiceType type;
    String message;
    Object data;
}

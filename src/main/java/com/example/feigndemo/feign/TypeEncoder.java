package com.example.feigndemo.feign;

import feign.RequestTemplate;
import feign.codec.Encoder;

import java.lang.reflect.Type;

public interface TypeEncoder extends Encoder {

    boolean supportsType(Type bodyType, RequestTemplate template);

}

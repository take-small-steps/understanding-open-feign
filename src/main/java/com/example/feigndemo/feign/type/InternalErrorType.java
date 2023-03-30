package com.example.feigndemo.feign.type;

import org.springframework.http.HttpStatus;

import java.util.Objects;

class InternalErrorType implements Exceptional {

    private final String code;
    private final String type;

    public InternalErrorType(HttpStatus httpStatus,
                             ErrorServiceType errorServiceType) {
        this.code = Objects.requireNonNull(httpStatus).value() + "." + Objects.requireNonNull(errorServiceType).getCode();
        this.type = httpStatus.name() + "_" + errorServiceType.name();
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getType() {
        return this.type;
    }
}

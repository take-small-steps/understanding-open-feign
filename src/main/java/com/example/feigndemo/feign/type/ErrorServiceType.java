package com.example.feigndemo.feign.type;

import java.util.Objects;

public enum ErrorServiceType {
    DEFAULT("00000", null);

    private final String code;
    private final String id;

    ErrorServiceType(String code,
                     String id) {
        this.code = Objects.requireNonNull(code);
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public String getId() {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("라우터 서비스 아이디가 필요합니다. : " + name());
        }
        return id;
    }
}

package com.example.feigndemo.feign.type;

import org.springframework.http.HttpStatus;

import java.util.Objects;
import java.util.stream.Stream;

public enum ServerErrorType implements Exceptional {

    /**
     * 5xx
     **/
    //500 unknown
    INTERNAL_SERVER_ERROR_DEFAULT(HttpStatus.INTERNAL_SERVER_ERROR, ErrorServiceType.DEFAULT.getCode()),
    INTERNAL_SERVER_ERROR_STATUS(HttpStatus.INTERNAL_SERVER_ERROR, "00001"),

    //501 deprecated, unsupported
    NOT_IMPLEMENTED_DEFAULT(HttpStatus.NOT_IMPLEMENTED, ErrorServiceType.DEFAULT.getCode()),
    NOT_IMPLEMENTED_DEPRECATED(HttpStatus.NOT_IMPLEMENTED, "00001"),
    NOT_IMPLEMENTED_UNSUPPORTED(HttpStatus.NOT_IMPLEMENTED, "00002"),

    //503 temporary occurred error. (back-end service / system / resource / connection timeout)
    SERVICE_UNAVAILABLE_DEFAULT(HttpStatus.SERVICE_UNAVAILABLE, ErrorServiceType.DEFAULT.getCode()),
    SERVICE_UNAVAILABLE_CONNECT(HttpStatus.SERVICE_UNAVAILABLE, "00003"),
    SERVICE_UNAVAILABLE_TIMEOUT(HttpStatus.SERVICE_UNAVAILABLE, "00004"),


    /**
     * not used
     **/
    //502 critical routing service
    BAD_GATEWAY(HttpStatus.BAD_GATEWAY, ErrorServiceType.DEFAULT.getCode()),
    //504 gateway timeout
    GATEWAY_TIMEOUT(HttpStatus.GATEWAY_TIMEOUT, ErrorServiceType.DEFAULT.getCode()),
    ;

    private final HttpStatus status;
    private final String detailCode;

    ServerErrorType(HttpStatus status,
                    String detailCode) {
        this.status = Objects.requireNonNull(status);
        this.detailCode = Objects.requireNonNull(detailCode);
    }

    @Override
    public String getCode() {
        return this.status.value() + "." + this.detailCode;
    }

    @Override
    public String getType() {
        return name();
    }

    public static Exceptional from(HttpStatus httpStatus,
                                   ErrorServiceType errorServiceType) {
        return Stream.of(values())
                .filter(type -> type.status.value() == httpStatus.value() && type.detailCode.equals(Objects.requireNonNull(errorServiceType).getCode()))
                .findFirst().map(c -> (Exceptional) c)
                .orElse(new InternalErrorType(httpStatus, errorServiceType));
    }
}

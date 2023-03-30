package com.example.feigndemo.feign.type;

import org.springframework.http.HttpStatus;

import java.util.Objects;

public enum ClientErrorType {

    FORBIDDEN_DEFAULT(HttpStatus.FORBIDDEN, ErrorServiceType.DEFAULT.getCode()),    // forbidden default, 셀프서비스 권한 추가에 사용
    FORBIDDEN_ACCESS_DENIED(HttpStatus.FORBIDDEN, "00001"),               // 접근권한 제어, RolesAllowed ( 로그인 사용자 / 회원등급 미달 ) 에서 발생
    FORBIDDEN_MEMBER_DORMACY(HttpStatus.FORBIDDEN, "00002"),              // 휴면사용자 제어, exception 발생하여 본인 인증 유도
    FORBIDDEN_MEMBER_UNDER_FOURTEEN(HttpStatus.FORBIDDEN, "00003"),       // 14세 미만 에러 발생
    FORBIDDEN_MEMBER_NEED_CERTIFICATION(HttpStatus.FORBIDDEN, "00004"),   // 일반 회원이면서 본인인증이 필요한 경우는 로그인시키지 않고 exception 발생
    FORBIDDEN_MEMBER_CERTIFICATION_FAILED(HttpStatus.FORBIDDEN, "00005"), // 본인인증 데이터 변조 확인
    FORBIDDEN_BUSINESS_ANALYSIS_LIMIT_PER_MINUTES(HttpStatus.FORBIDDEN, "00006"),   // 상권분석 최대 조회 수 초과 - ( 1분 최대 10회 )
    FORBIDDEN_ORDER_LOGIN_TEMP_BLOCKED(HttpStatus.FORBIDDEN, "00007"),        // 주문접수 임시 로그인 차단
    FORBIDDEN_BUSINESS_ANALYSIS_BLOCK_PER_MINUTES(HttpStatus.FORBIDDEN, "00008"),   // 상권분석 최대 조회 수 초과 - ( 1분 11회 초과 )
    /**
     * not used
     **/
    //408 read timeout
    REQUEST_TIMEOUT(HttpStatus.REQUEST_TIMEOUT, ErrorServiceType.DEFAULT.getCode()),
    ;

    private final HttpStatus status;
    private final String detailCode;

    ClientErrorType(HttpStatus status,
                    String detailCode) {
        this.status = Objects.requireNonNull(status);
        this.detailCode = Objects.requireNonNull(detailCode);
    }

    public String getCode() {
        return this.status.value() + "." + this.detailCode;
    }

    public String getType() {
        return name();
    }
}

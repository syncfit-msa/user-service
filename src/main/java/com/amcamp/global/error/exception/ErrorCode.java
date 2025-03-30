package com.amcamp.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    ID_TOKEN_VERIFICATION_FAILED(HttpStatus.UNAUTHORIZED, "ID 토큰 검증에 실패했습니다."),
    AUTH_NOT_FOUND(HttpStatus.UNAUTHORIZED, "사용자 인증 정보를 찾을 수 없습니다. 올바른 토큰으로 요청해주세요."),

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}

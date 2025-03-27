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

    SPOTIFY_EXCEPTION(HttpStatus.SERVICE_UNAVAILABLE, "스포티파이 라이브러리 사용 중 예외가 발생했습니다."),

    WISHLIST_NOT_FOUND(HttpStatus.NOT_FOUND, "위시리스트를 찾을 수 없습니다."),
    WISHLIST_MEMBER_MISMATCH(HttpStatus.FORBIDDEN, "위시리스트를 생성한 유저와 로그인된 계정이 일치하지 않습니다"),

    TRACK_NOT_FOUND(HttpStatus.NOT_FOUND, "트랙을 찾을 수 없습니다."),
    TRACK_MEMBER_MISMATCH(HttpStatus.FORBIDDEN, "트랙을 생성한 유저와 로그인된 계정이 일치하지 않습니다."),

    FIREBASE_UPLOAD_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "Firebase 파일 업로드 중 예외가 발생했습니다"),
    FIREBASE_IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "이미지를 찾을 수 없습니다."),
    FIREBASE_DOWNLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 다운로드 중 오류가 발생했습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}

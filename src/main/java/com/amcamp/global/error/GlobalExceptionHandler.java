package com.amcamp.global.error;

import com.amcamp.global.common.response.GlobalResponse;
import com.amcamp.global.error.exception.CustomException;
import com.amcamp.global.error.exception.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<GlobalResponse> handleCustomException(CustomException e) {
        final ErrorCode errorCode = e.getErrorCode();
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .status(errorCode.getHttpStatus())
                .message(errorCode.getMessage())
                .build();

        final GlobalResponse response = GlobalResponse.
                fail(errorCode.getHttpStatus().value(), errorResponse);

        return ResponseEntity.status(errorCode.getHttpStatus()).body(response);
    }
}

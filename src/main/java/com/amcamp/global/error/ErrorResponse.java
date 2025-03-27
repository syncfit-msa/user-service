package com.amcamp.global.error;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public class ErrorResponse {
    private final HttpStatus status;
    private final String message;

    @Builder
    public ErrorResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}

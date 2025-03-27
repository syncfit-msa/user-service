package com.amcamp.domain.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record SocialLoginResponse(String accessToken, @JsonIgnore String refreshToken) {
    public static SocialLoginResponse of(String accessToken, String refreshToken) {
        return new SocialLoginResponse(accessToken, refreshToken);
    }
}
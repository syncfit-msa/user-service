package com.amcamp.domain.auth.api;

import com.amcamp.domain.auth.application.AuthService;
import com.amcamp.domain.auth.dto.request.AuthCodeRequest;
import com.amcamp.domain.auth.dto.response.SocialLoginResponse;
import com.amcamp.global.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final CookieUtil cookieUtil;

    @PostMapping("/social-login")
    public ResponseEntity<SocialLoginResponse> memberSocialLogin(@RequestBody AuthCodeRequest request) {
        SocialLoginResponse response = authService.socialLoginMember(request);

        String refreshToken = response.refreshToken();
        HttpHeaders headers = cookieUtil.generateRefreshTokenCookie(refreshToken);

        return ResponseEntity.ok().headers(headers).body(response);
    }
}
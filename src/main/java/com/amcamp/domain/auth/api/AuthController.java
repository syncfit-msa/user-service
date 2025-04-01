package com.amcamp.domain.auth.api;

import com.amcamp.domain.auth.application.AuthService;
import com.amcamp.domain.auth.application.JwtTokenService;
import com.amcamp.domain.auth.dto.AccessTokenDto;
import com.amcamp.domain.auth.dto.RefreshTokenDto;
import com.amcamp.domain.auth.dto.request.AuthCodeRequest;
import com.amcamp.domain.auth.dto.response.SocialLoginResponse;
import com.amcamp.global.util.CookieUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtTokenService jwtTokenService;
    private final CookieUtil cookieUtil;

    @PostMapping("/social-login")
    public ResponseEntity<SocialLoginResponse> memberSocialLogin(@RequestBody AuthCodeRequest request) {
        SocialLoginResponse response = authService.socialLoginMember(request);

        String refreshToken = response.refreshToken();
        HttpHeaders headers = cookieUtil.generateRefreshTokenCookie(refreshToken);

        return ResponseEntity.ok().headers(headers).body(response);
    }


    @PostMapping("/reissue")
    public ResponseEntity<Void> reissueToken(HttpServletRequest request, HttpServletResponse response) {
        // 쿠키에서 refresh 토큰 추출
        String refreshToken = extractRefreshTokenFromCookie(request);

        // refresh 토큰 검증 (Redis에서 관리되는 refresh 토큰)
        RefreshTokenDto refreshTokenDto = jwtTokenService.retrieveRefreshToken(refreshToken);
        if (refreshTokenDto == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // access 토큰 재발급 (refresh 토큰은 별도로 갱신하지 않음)
        AccessTokenDto newAccessTokenDto = jwtTokenService.reissueAccessTokenIfExpired(null);

        // 새로운 access 토큰을 응답 헤더에 담아서 클라이언트에 전달
        response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + newAccessTokenDto.accessTokenValue());

        return ResponseEntity.ok().build();
    }

    private String extractRefreshTokenFromCookie(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, "refreshToken");
        return (cookie != null) ? cookie.getValue() : null;
    }
}
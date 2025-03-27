package com.amcamp.domain.auth.application;

import com.amcamp.global.error.exception.CustomException;
import com.amcamp.global.error.exception.ErrorCode;
import com.amcamp.infra.config.oauth.KakaoProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class IdTokenVerifier {

    private final KakaoProperties kakaoProperties;
    private final JwtDecoder jwtDecoder = buildDecoder();

    public OidcUser getOidcUser(String idToken) {
        Jwt jwt = getJwt(idToken);
        OidcIdToken oidcIdToken = getOidcIdToken(jwt);

        validateAudience(oidcIdToken);
        validateIssuer(oidcIdToken);
        validateExpiresAt(oidcIdToken);

        return new DefaultOidcUser(null, oidcIdToken);
    }

    private Jwt getJwt(String idToken) {
        return jwtDecoder.decode(idToken);
    }

    private JwtDecoder buildDecoder() {
        return NimbusJwtDecoder
                .withJwkSetUri("https://kauth.kakao.com/.well-known/jwks.json")
                .build();
    }

    private void validateAudience(OidcIdToken oidcIdToken) {
        String idTokenAudience = oidcIdToken.getAudience().get(0);

        if (idTokenAudience == null || !idTokenAudience.equals(kakaoProperties.clientId())) {
            throw new CustomException(ErrorCode.ID_TOKEN_VERIFICATION_FAILED);
        }
    }

    private void validateIssuer(OidcIdToken oidcIdToken) {
        String idTokenIssuer = oidcIdToken.getIssuer().toString();

        if (idTokenIssuer == null || !idTokenIssuer.equals("https://kauth.kakao.com")) {
            throw new CustomException(ErrorCode.ID_TOKEN_VERIFICATION_FAILED);
        }
    }

    private void validateExpiresAt(OidcIdToken oidcIdToken) {
        Instant expiresAt = oidcIdToken.getExpiresAt();

        if (expiresAt == null || expiresAt.isBefore(Instant.now())) {
            throw new CustomException(ErrorCode.ID_TOKEN_VERIFICATION_FAILED);
        }
    }

    private OidcIdToken getOidcIdToken(Jwt jwt) {
        return new OidcIdToken(
                jwt.getTokenValue(), jwt.getIssuedAt(), jwt.getExpiresAt(), jwt.getClaims());
    }
}
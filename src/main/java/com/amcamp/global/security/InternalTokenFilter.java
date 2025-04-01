package com.amcamp.global.security;

import com.amcamp.infra.config.internal.InternalTokenProperties;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class InternalTokenFilter implements HandlerInterceptor {

    private final InternalTokenProperties internalTokenProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String token = request.getHeader("X-Internal-Token");
        if (!internalTokenProperties.token().equals(token)) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write("Invalid internal token");
            return false;
        }
        return true;
    }
}

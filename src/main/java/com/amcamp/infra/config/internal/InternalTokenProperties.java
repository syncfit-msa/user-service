package com.amcamp.infra.config.internal;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "internal")
public record InternalTokenProperties(String token) {
}

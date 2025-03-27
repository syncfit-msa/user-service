package com.amcamp.infra.config.youtube;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("youtube")
public record YoutubeProperties(String apiKey) {
}

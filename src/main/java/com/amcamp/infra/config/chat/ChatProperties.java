package com.amcamp.infra.config.chat;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "google")
public record ChatProperties (
    String geminiBaseurl,
    String apiKey
){

}
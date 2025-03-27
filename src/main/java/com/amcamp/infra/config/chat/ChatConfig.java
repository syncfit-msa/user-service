package com.amcamp.infra.config.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Component
@RequiredArgsConstructor
public class ChatConfig {

    private final ChatProperties chatProperties;

    @Bean
    public RestClient chatRestClient() {
        return RestClient.builder()
                .baseUrl(chatProperties.geminiBaseurl())
                .defaultHeader("x-goog-api-key", chatProperties.apiKey())
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("Accept", "application/json")
                .build();
    }

    @Bean
    public ChatInterface chatInterface(RestClient client) {
        RestClientAdapter adapter = RestClientAdapter.create(client);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(ChatInterface.class);
    }
}

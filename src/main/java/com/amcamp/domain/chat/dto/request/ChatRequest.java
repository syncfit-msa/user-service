package com.amcamp.domain.chat.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRequest {

    private List<Content> contents;

    public ChatRequest(String text) {
        TextPart part = new TextPart(text);
        Content content = new Content(Collections.singletonList(part));
        this.contents = Collections.singletonList(content);
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Content {
        private List<TextPart> parts;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TextPart {
        private String text;
    }
}

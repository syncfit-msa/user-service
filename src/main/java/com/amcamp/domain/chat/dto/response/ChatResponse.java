package com.amcamp.domain.chat.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ChatResponse {

    private List<Candidate> candidates;

    @Getter
    public static class Candidate {
        private Content content;
    }

    @Getter
    @NoArgsConstructor
    public static class Content {
        private List<TextPart> parts;
    }

    @Getter
    @NoArgsConstructor
    public static class TextPart {
        private String text;
    }
}

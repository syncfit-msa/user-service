package com.amcamp.domain.chat.api;

import com.amcamp.domain.chat.application.ChatService;
import com.amcamp.domain.chat.application.PromptGenerator;
import com.amcamp.domain.chat.dto.response.GenreRecommendationResponse;
import com.amcamp.global.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/music")
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/recommendation")
    public GenreRecommendationResponse getRecommendation(@RequestBody Map<String, String> request) {
        String input = request.get("input");
        String genre = chatService.getRecommendGenre(input);

        return new GenreRecommendationResponse(genre);
    }


}

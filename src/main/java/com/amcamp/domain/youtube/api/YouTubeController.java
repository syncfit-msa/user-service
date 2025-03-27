package com.amcamp.domain.youtube.api;

import com.amcamp.domain.youtube.application.YouTubeService;
import com.amcamp.domain.youtube.dto.response.YouTubeVideoIdResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/music")
public class YouTubeController {

    private final YouTubeService youTubeService;

    @GetMapping("/youtube")
    public YouTubeVideoIdResponse getYouTubeLink(@RequestParam String query) throws IOException {
        return youTubeService.getYouTubeLink(query);
    }
}
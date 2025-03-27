package com.amcamp.domain.spotify.api;

import com.amcamp.domain.spotify.application.SpotifyService;
import com.amcamp.domain.spotify.dto.response.SpotifySearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/music")
public class SpotifyController {

    private final SpotifyService spotifyService;

    @GetMapping("/search")
    public List<SpotifySearchResponse> searchSpotify(@RequestParam List<String> genres) {
        return spotifyService.searchByGenre(genres);
    }
}

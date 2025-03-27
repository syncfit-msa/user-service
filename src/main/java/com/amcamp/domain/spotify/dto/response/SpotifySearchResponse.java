package com.amcamp.domain.spotify.dto.response;

public record SpotifySearchResponse(String artistName, String title, String albumName, String imageUrl) {
    public static SpotifySearchResponse of(String artistName, String title, String albumName, String imageUrl) {
        return new SpotifySearchResponse(artistName, title, albumName, imageUrl);
    }
}
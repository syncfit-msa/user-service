package com.amcamp.domain.track.dto.response;

import com.amcamp.domain.track.domain.Track;

public record TrackInfoResponse(
        Long trackId,
        String artistName,
        String title,
        String albumName,
        String imageUrl) {
    public static TrackInfoResponse from(Track track) {
        return new TrackInfoResponse(track.getId(), track.getArtistName(), track.getTitle(), track.getAlbumName(), track.getImageUrl());
    }
}

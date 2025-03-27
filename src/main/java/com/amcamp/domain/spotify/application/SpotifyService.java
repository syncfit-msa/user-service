package com.amcamp.domain.spotify.application;

import com.amcamp.domain.spotify.dto.response.SpotifySearchResponse;
import com.amcamp.global.error.exception.CustomException;
import com.amcamp.global.error.exception.ErrorCode;
import com.amcamp.global.util.MemberUtil;
import com.amcamp.infra.config.spotify.SpotifyConfig;
import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.*;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static com.neovisionaries.i18n.CountryCode.KR;

@Transactional
@Service
@RequiredArgsConstructor
public class SpotifyService {

    private final SpotifyConfig spotifyConfig;
    private final MemberUtil memberUtil;

    public List<SpotifySearchResponse> searchByGenre(List<String> genres) {
        memberUtil.getCurrentMember();

        Track[] tracks = getTrackInfoByGenre(genres);

        return Arrays.stream(tracks)
                .map(this::getTrackData)
                .toList();
    }

    /*
     * 장르에 따른 검색 결과를 반환합니다.
     */
    public Track[] getTrackInfoByGenre(List<String> genres) {
        try {
            SpotifyApi spotifyApi = new SpotifyApi.Builder()
                    .setAccessToken(spotifyConfig.generateAccessToken())
                    .build();

            Set<Track> uniqueTracks = new LinkedHashSet<>();

            for (String genre : genres) {
                String query = "genre:" + genre + " year:2024-2025";

                SearchTracksRequest searchTrackRequest = spotifyApi.searchTracks(query)
                        .market(KR)
                        .limit(10)
                        .build();

                Paging<Track> searchResult = searchTrackRequest.execute();

                List<Track> selectedTracks = Arrays.stream(searchResult.getItems())
                        .limit(3)
                        .toList();

                uniqueTracks.addAll(selectedTracks);

                if (uniqueTracks.size() >= 10) {
                    break;
                }
            }

            return uniqueTracks.toArray(new Track[0]);
        } catch (IOException | ParseException | SpotifyWebApiException e) {
            throw new CustomException(ErrorCode.SPOTIFY_EXCEPTION);
        }
    }

    private SpotifySearchResponse getTrackData(Track track) {
        ArtistSimplified[] artists = track.getArtists();
        String artistName = artists[0].getName();

        AlbumSimplified album = track.getAlbum();
        String albumName = album.getName();

        Image[] images = album.getImages();
        String imageUrl = (images.length > 0) ? images[0].getUrl() : "NO_IMAGE";

        return SpotifySearchResponse.of(artistName, track.getName(), albumName, imageUrl);
    }
}


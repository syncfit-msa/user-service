package com.amcamp.infra.config.spotify;

import com.amcamp.global.error.exception.CustomException;
import com.amcamp.global.error.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

import java.io.IOException;

@Component
@Slf4j
public class SpotifyConfig {

    private final SpotifyApi spotifyApi;

    private SpotifyConfig(SpotifyProperties spotifyProperties) {
        this.spotifyApi =
                new SpotifyApi.Builder()
                        .setClientId(spotifyProperties.clientId())
                        .setClientSecret(spotifyProperties.clientSecret())
                        .build();
    }

    public String generateAccessToken() {
        ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();
        try {
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());
            return spotifyApi.getAccessToken();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.error(e.getMessage());
            throw new CustomException(ErrorCode.SPOTIFY_EXCEPTION);
        }
    }
}

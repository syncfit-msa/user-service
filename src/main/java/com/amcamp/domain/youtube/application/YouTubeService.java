package com.amcamp.domain.youtube.application;

import com.amcamp.domain.youtube.dto.response.YouTubeVideoIdResponse;
import com.amcamp.global.util.MemberUtil;
import com.amcamp.infra.config.youtube.YoutubeProperties;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeRequestInitializer;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class YouTubeService {

    private final MemberUtil memberUtil;
    private final YoutubeProperties youtubeProperties;

    public YouTubeVideoIdResponse getYouTubeLink(String query) throws IOException {
        memberUtil.getCurrentMember();

        YouTube youtube = new YouTube.Builder(
                new NetHttpTransport(),
                new JacksonFactory(),
                request -> {
                })
                .setYouTubeRequestInitializer(new YouTubeRequestInitializer(youtubeProperties.apiKey()))
                .build();

        YouTube.Search.List search = youtube.search().list(Collections.singletonList("id"));
        search.setQ(query);
        search.setMaxResults(1L);

        SearchListResponse searchResponse = search.execute();
        List<SearchResult> results = searchResponse.getItems();

        if (!results.isEmpty()) {
            String videoId = results.get(0).getId().getVideoId();
            return new YouTubeVideoIdResponse(videoId);
        }

        return null;
    }
}
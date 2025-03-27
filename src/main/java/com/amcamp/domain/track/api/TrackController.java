package com.amcamp.domain.track.api;

import com.amcamp.domain.track.application.TrackService;
import com.amcamp.domain.track.dto.request.TrackCreateRequest;
import com.amcamp.domain.track.dto.response.TrackInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tracks")
public class TrackController {

    private final TrackService trackService;

    @PostMapping
    public ResponseEntity<Void> trackCreate(@RequestBody TrackCreateRequest request) {
        trackService.createTrack(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{trackId}")
    public ResponseEntity<Void> trackDelete(@PathVariable Long trackId) {
        trackService.deleteTrack(trackId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{wishlistId}")
    public List<TrackInfoResponse> trackFindAll(@PathVariable Long wishlistId) {
        return trackService.findAllTrack(wishlistId);
    }
}

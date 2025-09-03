package com.github.sanhak.track.controller.dto.response;

import java.util.List;

public record TrackListResponse(
        List<TrackSummary> tracks
) {
    public static TrackListResponse from(List<TrackSummary> tracks) {
        return new TrackListResponse(tracks);
    }
}

package com.github.sanhak.track.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TrackRecordSummary(
        @Schema(description = "최근 플레이 일시", example = "2025-08-23T12:34:56Z")
        LocalDateTime lastPlayedAt,

        @Schema(description = "최근 플레이 정확도", example = "0.75")
        Double lastAccuracy,

        @Schema(description = "최고 정확도를 달성한 일시", example = "2025-08-20T09:12:00Z")
        LocalDateTime bestPlayedAt,

        @Schema(description = "최고 정확도", example = "0.9")
        Double bestAccuracy
) {

}
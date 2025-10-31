package com.github.sanhak.playresult.controller.dto.response;

import com.github.sanhak.instrument.repository.InstrumentType;
import com.github.sanhak.playresult.repository.PlayResultEntity;
import com.github.sanhak.sheet.repository.SheetDifficulty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record PlayResultFindAllResponse(
        @Schema(description = "악기별 곡에 대한 최대 정확도 기록 목록")
        List<PlayResultSummary> playResults
) {
    public record PlayResultSummary(
            @Schema(description = "기록 ID", example = "1")
            Long id,

            @Schema(description = "트랙 ID", example = "1")
            Long trackId,

            @Schema(description = "트랙 제목", example = "별달거리")
            String trackTitle,

            @Schema(description = "난이도", example = "normal")
            SheetDifficulty difficulty,

            @Schema(description = "악기", example = "buk")
            InstrumentType instrument,

            @Schema(description = "정확도(0.0~1.0)", example = "0.875")
            Double accuracy,

            @Schema(description = "점수", example = "811250")
            Integer score,

            @Schema(description = "플레이 시각(UTC)", example = "2025-08-23T12:34:56Z")
            LocalDateTime playedAt
    ) {
            public static PlayResultSummary from(PlayResultEntity result) {
                    return new PlayResultSummary(
                            result.getId(),
                            result.getSheet().getTrack().getId(),
                            result.getSheet().getTrack().getTitle(),
                            result.getSheet().getDifficulty(),
                            result.getSheet().getInstrument().getType(),
                            result.getAccuracy(),
                            result.getScore(),
                            result.getPlayedAt()
                    );
            }
    }
}
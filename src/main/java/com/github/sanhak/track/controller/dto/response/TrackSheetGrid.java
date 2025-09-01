package com.github.sanhak.track.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.sanhak.instrument.repository.InstrumentType;
import com.github.sanhak.sheet.repository.SheetDifficulty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record TrackSheetGrid(
        @Schema(description = "트랙 ID", example = "1")
        Long trackId,

        @Schema(description = "악기", example = "buk")
        InstrumentType instrument,

        @Schema(description = "시트 ID", example = "123")
        Long sheetId,

        @Schema(description = "난이도", example = "normal")
        SheetDifficulty difficulty,

        @Schema(description = "총 마디 수", example = "22")
        Integer totalMeasures,

        @Schema(description = "슬롯 간 간격(ms)", example = "175")
        Integer intervalMs,

        @Schema(description = "소요 시간(ms)", example = "30800")
        Long durationMs,

        @Schema(description = "마디 단위 데이터(연주/가사)")
        List<Measure> measures,

        @Schema(description = "S3에 업로드된 트랙 오디오 URL")
        String audioUrl
) {

    public record Measure(
            MeasureSlots strokes,

            @JsonInclude(JsonInclude.Include.NON_NULL)
            MeasureSlots lyrics
    ) {}

    // 한 마디의 8칸 그리드
    public record MeasureSlots(
            String first,
            String second,
            String third,
            String fourth,
            String fifth,
            String sixth,
            String seventh,
            String eighth
    ) {

    }
}
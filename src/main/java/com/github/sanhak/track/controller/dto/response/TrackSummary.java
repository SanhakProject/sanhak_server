package com.github.sanhak.track.controller.dto.response;

import com.github.sanhak.instrument.repository.InstrumentType;
import com.github.sanhak.sheet.repository.SheetDifficulty;
import com.github.sanhak.track.repository.TrackEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record TrackSummary(
        @Schema(description = "트랙 ID", example = "1.0") Long trackId,

        @Schema(description = "트랙 제목", example = "별달거리") String title,

        @Schema(description = "악기", example = "장구") InstrumentType instrument,

        @Schema(description = "악기 선택에 따른 트랙 난이도", example = "HARD") SheetDifficulty difficulty,

        @Schema(description = "소요 시간(ms)", example = "38000") Long durationMs,

        @Schema(description = "트랙 오디오 키", example = "uploads/byeoldalgeori.mp3") String audioKey,

        @Schema(description = "S3에 업로드된 트랙 오디오 URL", example = "https://my-bucket.s3.ap-northeast-2.amazonaws" +
                ".com/uploads/byeoldalgeori.mp3") String audioUrl,

        @Schema(description = "기록 요약") TrackRecordSummary record

) {

    public TrackSummary withAudioUrl(String audioUrl) {
        return new TrackSummary(
                this.trackId,
                this.title,
                this.instrument,
                this.difficulty,
                this.durationMs,
                this.audioKey,
                audioUrl,
                this.record
        );
    }

    public static TrackSummary from(
            TrackEntity trackEntity,
            InstrumentType instrument,
            SheetDifficulty difficulty,
            String audioUrl,
            TrackRecordSummary record
    ) {

        return TrackSummary.builder()
                .trackId(trackEntity.getId())
                .title(trackEntity.getTitle())
                .instrument(instrument)
                .difficulty(difficulty)
                .durationMs(trackEntity.getDurationMs())
                .audioKey(trackEntity.getAudioKey())
                .audioUrl(audioUrl)
                .record(record)
                .build();
    }
}

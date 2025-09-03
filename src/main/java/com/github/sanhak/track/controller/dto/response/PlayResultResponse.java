package com.github.sanhak.track.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record PlayResultResponse(
        @Schema(description = "게임 기록 ID", example = "987")
        Long gameId,

        @Schema(description = "최종 점수", example = "811250")
        Integer score,

        @Schema(description = "정확도(0.0~1.0)", example = "0.875")
        Double accuracy,

        @Schema(description = "첫 번째로 틀린 마디 번호(선택, 1부터)", example = "3")
        Integer firstFailedMeasureIdx,

        @Schema(description = "첫 번째로 틀린 슬롯 번호(선택, 1~8)", example = "5")
        Integer firstFailedSlotIdx,

        @Schema(description = "최대 콤보 수", example = "57")
        Integer maxCombo,

        @Schema(description = "플레이 종료 시각", example = "2025-08-23T12:34:56Z")
        LocalDateTime playedAt
) {

}
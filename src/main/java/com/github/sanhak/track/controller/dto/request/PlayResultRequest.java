package com.github.sanhak.track.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

public record PlayResultRequest(

        @Schema(description = "전체 노트 수", example = "240")
        @NotNull(message = "전체 노트 수는 필수입니다.")
        @Min(value = 1, message = "전체 노트 수는 1 이상입니다.")
        Integer total,

        @Schema(description = "맞춘 노트 수", example = "210")
        @NotNull(message = "맞춘 노트 수는 필수입니다.")
        @Min(value = 0, message = "맞춘 노트 수는 0 이상입니다.")
        Integer success,

        @Schema(description = "틀린 노트 수", example = "30")
        @NotNull(message = "틀린 노트 수는 필수입니다.")
        @Min(value = 0, message = "틀린 노트 수는 0 이상입니다.")
        Integer fail,

        @Schema(description = "최대 콤보(연속 성공 수)", example = "57")
        @NotNull(message = "최대 콤보 수는 필수입니다.")
        @Min(value = 0, message = "최대 콤보는 0 이상입니다.")
        Integer maxCombo,

        @Schema(description = "첫 번째로 틀린 마디 번호(선택, 1부터)", example = "3")
        @Min(value = 1, message = "마디 번호는 1 이상입니다.")
        Integer firstFailedMeasureIdx,

        @Schema(description = "첫 번째로 틀린 슬롯 번호(선택, 1~8)", example = "5")
        @Min(value = 1, message = "슬롯 번호는 1 이상입니다.")
        @Max(value = 8, message = "슬롯 번호는 8 이하여야 합니다.")
        Integer firstFailedSlotIdx
) {

}
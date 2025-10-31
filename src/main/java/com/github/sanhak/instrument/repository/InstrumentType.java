package com.github.sanhak.instrument.repository;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Schema(description = "한국 전통악기 타입")
public enum InstrumentType {
    @Schema(description = "북 - 한국 전통 타악기")
    BUK("북"),

    @Schema(description = "징 - 한국 전통 타악기")
    JING("징"),

    @Schema(description = "장구 - 한국 전통 타악기")
    JANGGU("장구"),

    @Schema(description = "꽹가리 - 한국 전통 타악기")
    KKWAENGGWARI("꽹가리");

    private final String description;

    @JsonValue
    public String getName() {
        return this.name();
    }

    @JsonCreator
    public static InstrumentType fromString(String value) {
        for (InstrumentType type : InstrumentType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown InstrumentType: " + value);
    }
}

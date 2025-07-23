package com.github.sanhak.instrument.repository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InstrumentType {
    BUK("북"),
    JING("징"),
    JANGGU("장구"),
    KKWAENGGWARI("꾕가리");

    private final String description;
}

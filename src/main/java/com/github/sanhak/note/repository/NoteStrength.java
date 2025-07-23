package com.github.sanhak.note.repository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NoteStrength {
    STRONG("강"),
    MEDIUM("중간"),
    WEAK("약");

    private final String description;
}

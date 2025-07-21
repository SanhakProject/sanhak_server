package com.github.sanhak.user.repository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthType {

    PHONE("전화번호"),
    KAKAO("카카오");

    private final String description;
}

package com.github.sanhak.global.dto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(staticName = "of")
public class Request<Data> {
    LocalDateTime timestamp;

    @Valid
    Data data;
}

package com.github.sanhak.playresult.controller;

import com.github.sanhak.global.security.UserAuthInfo;
import com.github.sanhak.playresult.controller.dto.response.PlayResultFindAllResponse;
import com.github.sanhak.playresult.service.PlayResultService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/me/play-results")
@RequiredArgsConstructor
public class PlayResultController implements PlayResultApi {
    private final PlayResultService playResultService;

    @GetMapping
    public ResponseEntity<PlayResultFindAllResponse> getPlayResults(
            @AuthenticationPrincipal UserAuthInfo userAuthInfo,
            @PageableDefault(size = 10, sort = "playedAt", direction = Sort.Direction.DESC) @ParameterObject Pageable pageable
            )
        {
            return ResponseEntity.ok(playResultService.getPlayResults(userAuthInfo.getUserId(), pageable));
        }
}
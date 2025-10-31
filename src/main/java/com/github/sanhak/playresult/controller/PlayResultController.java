package com.github.sanhak.playresult.controller;

import com.github.sanhak.global.dto.Response;
import com.github.sanhak.global.security.UserAuthInfo;
import com.github.sanhak.playresult.controller.dto.response.PlayResultFindAllResponse;
import com.github.sanhak.playresult.service.PlayResultService;
import lombok.RequiredArgsConstructor;
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
    public Response<PlayResultFindAllResponse> getPlayResults(
            @AuthenticationPrincipal UserAuthInfo userAuthInfo
    ) {
        return Response.ok(playResultService.getPlayResults(userAuthInfo.getUserId()));
    }
}
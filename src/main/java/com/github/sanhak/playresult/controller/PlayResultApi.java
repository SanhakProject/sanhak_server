package com.github.sanhak.playresult.controller;

import com.github.sanhak.global.dto.Response;
import com.github.sanhak.global.security.UserAuthInfo;
import com.github.sanhak.playresult.controller.dto.response.PlayResultFindAllResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;


@Tag(name = "Play Results", description = "연주 기록 조회 API - 사용자의 과거 연주 결과 조회")
@SecurityRequirement(name = "JWT")
public interface PlayResultApi {

    @Operation(
            summary = "연주 기록 목록 조회",
            description = "로그인한 사용자의 악기별 곡에 대한 최대 정확도 기록을 최신순으로 조회합니다. 각 Sheet마다 가장 높은 정확도의 기록만 반환합니다."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "연주 기록 조회 성공"
                    )
            }
    )
    Response<PlayResultFindAllResponse> getPlayResults(
            @AuthenticationPrincipal UserAuthInfo userAuthInfo
    );
}

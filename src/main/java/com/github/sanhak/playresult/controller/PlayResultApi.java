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
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;


@Tag(name = "Play Results", description = "연주 기록 조회 API - 사용자의 과거 연주 결과 조회")
@SecurityRequirement(name = "JWT")
public interface PlayResultApi {

    @Operation(
            summary = "연주 기록 목록 조회 (페이징)",
            description = "로그인한 사용자의 과거 연주 기록을 최신순으로 페이징하여 조회합니다. 점수, 정확도, 콤보 등의 상세 정보를 포함합니다."
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
            @AuthenticationPrincipal UserAuthInfo userAuthInfo,
            @Parameter(description = "페이지 정보 (page, size, sort)", example = "page=0&size=10&sort=playedAt,desc")
            @PageableDefault(size = 10, sort = "playedAt", direction = Sort.Direction.DESC) @ParameterObject Pageable pageable
    );
}

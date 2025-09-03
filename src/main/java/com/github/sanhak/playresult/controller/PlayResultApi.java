package com.github.sanhak.playresult.controller;

import com.github.sanhak.global.security.UserAuthInfo;
import com.github.sanhak.playresult.controller.dto.response.PlayResultFindAllResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;


@Tag(name = "Play Results", description = "지난 기록(집계) 조회 API")
public interface PlayResultApi {

    @Operation(
            summary = "지난 기록 전체 조회",
            description = "사용자의 지난 연주 기록을 최신순으로 페이징 조회하는 GET API"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "연주 데이터 조회 성공"
                    )
            }
    )
    ResponseEntity<PlayResultFindAllResponse> getPlayResults(
            @AuthenticationPrincipal UserAuthInfo userAuthInfo,
            @PageableDefault(size = 10, sort = "playedAt", direction = Sort.Direction.DESC) @ParameterObject Pageable pageable
    );
}

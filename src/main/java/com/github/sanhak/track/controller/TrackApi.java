package com.github.sanhak.track.controller;

import com.github.sanhak.global.dto.Response;
import com.github.sanhak.global.security.UserAuthInfo;
import com.github.sanhak.instrument.repository.InstrumentType;
import com.github.sanhak.track.controller.dto.request.PlayResultRequest;
import com.github.sanhak.track.controller.dto.response.PlayResultResponse;
import com.github.sanhak.track.controller.dto.response.TrackListResponse;
import com.github.sanhak.track.controller.dto.response.TrackSheetGrid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Tracks", description = "전통악기 연주 게임 API - 트랙 목록, 악보 조회, 연주 결과 제출")
@SecurityRequirement(name = "JWT")
public interface TrackApi {
    @Operation(
            summary = "트랙 목록 조회 (악기별)",
            description = "선택한 전통악기(북/장구/꽹가리/징)에 해당하는 트랙(곡) 목록과 사용자의 최고 기록을 반환합니다."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                        responseCode = "200",
                        description = "트랙 목록 조회 성공"
                    )
            }
    )
    Response<TrackListResponse> getAllTracksByInstrument(
            @AuthenticationPrincipal UserAuthInfo userAuthInfo,
            @Parameter(description = "악기 타입 (BUK: 북, JANGGU: 장구, KKWAENGGWARI: 꽹가리, JING: 징)", required = true)
            InstrumentType instrument
    );

    @Operation(
            summary = "연주 악보 데이터 조회",
            description = "특정 트랙과 악기에 해당하는 연주 악보 데이터(음표 그리드)를 반환합니다. 게임 시작 전 호출하여 악보를 로드합니다."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "악보 데이터 조회 성공"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "트랙 또는 악보를 찾을 수 없음"
                    )
            }
    )
    Response<TrackSheetGrid> getTrackSheetGridByInstrument(
            @Parameter(description = "트랙 ID", required = true) Long trackId,
            @Parameter(description = "악기 타입 (BUK: 북, JANGGU: 장구, KKWAENGGWARI: 꽹가리, JING: 징)", required = true) InstrumentType instrument
    );

    @Operation(
            summary = "연주 결과 제출",
            description = "게임 종료 후 연주 결과(성공/실패/콤보 등)를 제출하면 점수와 정확도를 계산하여 저장하고 결과를 반환합니다."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "연주 결과 저장 성공"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "잘못된 연주 결과 데이터"
                    )
            }
    )
    Response<PlayResultResponse> submitPlayResult(
            @AuthenticationPrincipal UserAuthInfo userAuthInfo,
            @Parameter(description = "트랙 ID", required = true) Long trackId,
            @Parameter(description = "악기 타입 (BUK: 북, JANGGU: 장구, KKWAENGGWARI: 꽹가리, JING: 징)", required = true) InstrumentType instrument,
            @RequestBody PlayResultRequest request
    );
}

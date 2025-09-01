package com.github.sanhak.track.controller;

import com.github.sanhak.global.security.UserAuthInfo;
import com.github.sanhak.instrument.repository.InstrumentType;
import com.github.sanhak.track.controller.dto.request.PlayResultRequest;
import com.github.sanhak.track.controller.dto.response.PlayResultResponse;
import com.github.sanhak.track.controller.dto.response.TrackListResponse;
import com.github.sanhak.track.controller.dto.response.TrackSheetGrid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Tracks", description = "노래 목록/연주 데이터/연주 완료 API")

public interface TrackApi {
    @Operation(
            summary = "노래 목록 조회",
            description = "선택한 악기(instrument) 기준으로 노래 목록을 반환하는 GET API"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                        responseCode = "200",
                        description = "전체 노래 목록 조회 성공"
                    )
            }
    )
    ResponseEntity<TrackListResponse> getAllTracksByInstrument(
            @AuthenticationPrincipal UserAuthInfo userAuthInfo,
            InstrumentType instrument
    );

    @Operation(
            summary = "연주 데이터 조회",
            description = "트랙 ID와 악기에 해당하는 연주 데이터를 반환하는 GET API"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "연주 데이터 조회 성공"
                    )
            }
    )
    ResponseEntity<TrackSheetGrid> getTrackSheetGridByInstrument(Long trackId, InstrumentType instrument);

    @Operation(
            summary = "연주 완료 제출",
            description = "프론트에서 집계한 결과(total, success, fail, maxCombo)를 전송하면 서버가 점수/정확도를 계산해 저장하고 결과를 반환하는 POST API"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "저장 성공"
                    )
            }
    )
    ResponseEntity<PlayResultResponse> submitPlayResult(
            @AuthenticationPrincipal UserAuthInfo userAuthInfo,
            Long trackId,
            InstrumentType instrument,
            @RequestBody PlayResultRequest request
    );
}

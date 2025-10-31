package com.github.sanhak.track.controller;

import com.github.sanhak.global.dto.Response;
import com.github.sanhak.global.security.UserAuthInfo;
import com.github.sanhak.instrument.repository.InstrumentType;
import com.github.sanhak.track.controller.dto.request.PlayResultRequest;
import com.github.sanhak.track.controller.dto.response.PlayResultResponse;
import com.github.sanhak.track.controller.dto.response.TrackListResponse;
import com.github.sanhak.track.controller.dto.response.TrackSheetGrid;
import com.github.sanhak.track.service.TrackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tracks")
@RequiredArgsConstructor
public class TrackController implements TrackApi {

    private final TrackService trackService;

    @GetMapping
    public Response<TrackListResponse> getAllTracksByInstrument(
            @AuthenticationPrincipal UserAuthInfo userAuthInfo,
            @RequestParam InstrumentType instrument
    ) {
        return Response.ok(trackService.getAllTracksByInstrument(userAuthInfo.getUserId(), instrument));
    }

    @GetMapping("/{trackId}/{instrument}")
    public Response<TrackSheetGrid> getTrackSheetGridByInstrument(
            @PathVariable Long trackId,
            @PathVariable InstrumentType instrument
    ) {
        return Response.ok(trackService.getTrackSheetGridByInstrument(trackId, instrument));
    }

    @PostMapping("/{trackId}/{instrument}")
    public Response<PlayResultResponse> submitPlayResult(
            @AuthenticationPrincipal UserAuthInfo userAuthInfo,
            @PathVariable Long trackId,
            @PathVariable InstrumentType instrument,
            @Valid @RequestBody PlayResultRequest request
    ) {
        return Response.ok(trackService.submitPlayResult(userAuthInfo.getUserId(), trackId, instrument, request));
    }
}

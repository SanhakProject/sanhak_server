package com.github.sanhak.track.service;

import com.github.sanhak.global.external.s3.S3Service;
import com.github.sanhak.note.repository.NoteEntity;
import com.github.sanhak.playresult.exception.PlayResultException;
import com.github.sanhak.playresult.exception.PlayResultExceptionCode;
import com.github.sanhak.playresult.repository.PlayResultEntity;
import com.github.sanhak.playresult.repository.PlayResultRepository;
import com.github.sanhak.note.repository.NoteRepository;
import com.github.sanhak.sheet.exception.SheetException;
import com.github.sanhak.sheet.exception.SheetExceptionCode;
import com.github.sanhak.sheet.repository.SheetEntity;
import com.github.sanhak.sheet.repository.SheetRepository;
import com.github.sanhak.track.controller.dto.request.PlayResultRequest;
import com.github.sanhak.track.controller.dto.response.PlayResultResponse;
import com.github.sanhak.track.controller.dto.response.TrackListResponse;

import com.github.sanhak.instrument.repository.InstrumentType;
import com.github.sanhak.track.controller.dto.response.TrackSheetGrid;
import com.github.sanhak.track.controller.dto.response.TrackSheetGrid.Measure;
import com.github.sanhak.track.controller.dto.response.TrackSummary;
import com.github.sanhak.track.exception.TrackException;
import com.github.sanhak.track.exception.TrackExceptionCode;
import com.github.sanhak.track.repository.TrackEntity;
import com.github.sanhak.track.repository.TrackRepository;
import com.github.sanhak.user.repository.UserEntity;
import com.github.sanhak.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TrackService {

    private final TrackRepository trackRepository;
    private final SheetRepository sheetRepository;
    private final S3Service s3Service;
    private final NoteRepository noteRepository;
    private final PlayResultRepository playResultRepository;
    private final UserService userService;
    private final MeasureGridAssembler measureGridAssembler;

    public TrackListResponse getAllTracksByInstrument(Long userId, InstrumentType instrument) {

        List<TrackSummary> tracks = trackRepository.findAllByInstrument(userId, instrument);

        if (tracks.isEmpty()) {
            throw new TrackException(TrackExceptionCode.TRACK_NOT_FOUND);
        }

        tracks = tracks.stream().map(it -> it.withAudioUrl(generateAudioUrl(it.audioKey()))).toList();

        return TrackListResponse.from(tracks);
    }

    public TrackSheetGrid getTrackSheetGridByInstrument(Long trackId, InstrumentType instrument) {

        SheetEntity sheet = sheetRepository.findByTrackIdAndInstrument(trackId, instrument)
                .orElseThrow(() -> new SheetException(SheetExceptionCode.SHEET_NOT_FOUND));

        TrackEntity track = sheet.getTrack();

        List<NoteEntity> notes = noteRepository.findBySheetIdOrderByMeasureIdxAscSlotIdxAsc(sheet.getId());

        List<Measure> measures = measureGridAssembler.buildFromNotes(sheet.getTotalMeasures(), notes);

        String audioUrl = generateAudioUrl(track.getAudioKey());

        return new TrackSheetGrid(
                track.getId(),
                instrument,
                sheet.getId(),
                sheet.getDifficulty(),
                sheet.getTotalMeasures(),
                sheet.getIntervalMs(),
                track.getDurationMs(),
                measures,
                audioUrl
        );
    }

    private String generateAudioUrl(String audioKey) {

        if (audioKey == null || audioKey.isBlank()) {
            return null;
        }
        try {
            return s3Service.publicUrl(audioKey);
        } catch (Exception e) {
            throw new TrackException(TrackExceptionCode.TRACK_AUDIO_URL_GENERATION_FAILED);
        }
    }

    @Transactional
    public PlayResultResponse submitPlayResult(
            Long userId,
            Long trackId,
            InstrumentType instrument,
            @Valid PlayResultRequest request
    ) {

        UserEntity user = userService.findUserById(userId);

        if (request.total() == 0) {
            throw new PlayResultException(PlayResultExceptionCode.ZERO_TOTAL);
        }
        if (!request.total().equals(request.success() + request.fail())) {
            throw new PlayResultException(PlayResultExceptionCode.INVALID_TOTAL);
        }
        if (request.maxCombo() > request.success()) {
            throw new PlayResultException(PlayResultExceptionCode.INVALID_MAX_COMBO);
        }

        SheetEntity sheet = sheetRepository.findByTrackIdAndInstrument(trackId, instrument)
                .orElseThrow(() -> new TrackException(TrackExceptionCode.TRACK_NOT_FOUND));

        Integer firstMeasure = request.firstFailedMeasureIdx();
        Integer firstSlot    = request.firstFailedSlotIdx();

        if (firstMeasure != null && firstMeasure < 1) {
            throw new PlayResultException(PlayResultExceptionCode.INVALID_FIRST_FAILED_MEASURE);
        }
        if (firstSlot != null && (firstSlot < 1 || firstSlot > 8)) {
            throw new PlayResultException(PlayResultExceptionCode.INVALID_FIRST_FAILED_SLOT);
        }

        // 점수 계산 (정확도 90% + 콤보 10% = 총 1,000,000)
        double accuracy = request.success().doubleValue() / request.total().doubleValue();
        int baseScore = (int) Math.round(accuracy * 900_000);
        int comboScore = (int) Math.round(
                100_000.0 * (request.maxCombo().doubleValue() / request.total().doubleValue()));
        int score = baseScore + comboScore;

        LocalDateTime now = LocalDateTime.now();
        PlayResultEntity result = playResultRepository.save(PlayResultEntity.builder()
                .user(user)
                .sheet(sheet)
                .success(request.success())
                .fail(request.fail())
                .firstFailedMeasureIdx(firstMeasure)
                .firstFailedSlotIdx(firstSlot)
                .maxCombo(request.maxCombo())
                .accuracy(accuracy)
                .score(score)
                .playedAt(now)
                .build());

        return new PlayResultResponse(
                result.getId(),
                score,
                accuracy,
                result.getFirstFailedMeasureIdx(),
                result.getFirstFailedSlotIdx(),
                request.maxCombo(),
                now
        );
    }
}
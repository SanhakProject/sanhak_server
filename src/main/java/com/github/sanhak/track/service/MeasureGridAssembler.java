package com.github.sanhak.track.service;

import com.github.sanhak.note.exception.NoteException;
import com.github.sanhak.note.exception.NoteExceptionCode;
import com.github.sanhak.note.repository.NoteEntity;
import com.github.sanhak.track.controller.dto.response.TrackSheetGrid;
import com.github.sanhak.track.controller.dto.response.TrackSheetGrid.MeasureSlots;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class MeasureGridAssembler {

    private static final int SLOTS_PER_MEASURE = 8;
    private static final int SLOT_MIN = 1;
    private static final int SLOT_MAX = SLOTS_PER_MEASURE;

    // 노트 목록 → (strokes/lyrics) 마디 → measures[{strokes, lyrics}]
    public List<TrackSheetGrid.Measure> buildFromNotes(int totalMeasures, List<NoteEntity> notes) {
        log.info("[노트→마디] 시작: 전체 마디 수={}, 노트 개수={}", totalMeasures, (notes == null ? "null" : notes.size()));
        if (totalMeasures <= 0 || notes == null || notes.isEmpty()) {
            throw new NoteException(NoteExceptionCode.MEASURE_OR_NOTE_EMPTY);
        }

        List<MeasureSlots> strokes = buildStrokeGrid(totalMeasures, notes);
        List<MeasureSlots> lyrics;
        if (hasAnyLyrics(notes)) {
            lyrics = buildLyricGrid(totalMeasures, notes);
        } else {
            lyrics = null;
        }

        ArrayList<TrackSheetGrid.Measure> measures = new ArrayList<>(totalMeasures);
        for (int i = 0; i < totalMeasures; i++) {
            MeasureSlots s = strokes.get(i);
            MeasureSlots l = (lyrics != null ? lyrics.get(i) : null);
            measures.add(new TrackSheetGrid.Measure(s, l));
        }
        log.info("[노트→마디] 완료: 생성된 마디 수={}", totalMeasures);
        return measures;
    }

    // 연주소리 마디(8칸) 구성
    private static List<MeasureSlots> buildStrokeGrid(int totalMeasures, List<NoteEntity> notes) {

        List<MeasureSlots> strokeSlotsPerMeasure = createEmptyMeasureGrid(totalMeasures);
        for (NoteEntity n : notes) {
            int m = n.getMeasureIdx() - 1;
            int s = n.getSlotIdx();
            if (m < 0 || m >= totalMeasures || s < SLOT_MIN || s > SLOT_MAX) {
                throw new NoteException(NoteExceptionCode.NOTE_INDEX_OUT_OF_RANGE);
            }
            strokeSlotsPerMeasure.set(m, applyNoteToSlot(strokeSlotsPerMeasure.get(m), s, defaultIfNull(n.getStroke())));
        }
        log.info("[연주소리 마디] 완료: 마디 수={}", totalMeasures);
        return strokeSlotsPerMeasure;
    }

    // 가사 마디(8칸) 구성
    private static List<MeasureSlots> buildLyricGrid(int totalMeasures, List<NoteEntity> notes) {

        List<MeasureSlots> lyricSlotsPerMeasure = createEmptyMeasureGrid(totalMeasures);
        for (NoteEntity n : notes) {
            int m = n.getMeasureIdx() - 1;
            int s = n.getSlotIdx();
            if (m < 0 || m >= totalMeasures || s < SLOT_MIN || s > SLOT_MAX) {
                throw new NoteException(NoteExceptionCode.LYRICS_INDEX_OUT_OF_RANGE);
            }
            lyricSlotsPerMeasure.set(m, applyNoteToSlot(lyricSlotsPerMeasure.get(m), s, defaultIfNull(n.getLyric())));
        }
        log.info("[가사 마디] 완료: 마디 수={}", totalMeasures);
        return lyricSlotsPerMeasure;
    }

    // 빈 마디 생성
    private static List<MeasureSlots> createEmptyMeasureGrid(int total) {

        ArrayList<MeasureSlots> emptyMeasures = new ArrayList<>(Math.max(total, 0));
        for (int i = 0; i < total; i++) {
            emptyMeasures.add(new MeasureSlots("", "", "", "", "", "", "", ""));
        }
        return emptyMeasures;
    }

    // 노트 정보를 받아서 마디(8칸)의 특정 슬롯에 값 채워 넣는 함수
    private static MeasureSlots applyNoteToSlot(MeasureSlots ms, int slot, String value) {
        String[] slots = new String[] {
                ms.first(), ms.second(), ms.third(), ms.fourth(),
                ms.fifth(), ms.sixth(), ms.seventh(), ms.eighth()
        };

        int idx = slot - 1; // 1~8 -> 0~7
        if (idx < 0 || idx >= 8) {
            throw new NoteException(NoteExceptionCode.NOTE_INDEX_OUT_OF_RANGE);
        }

        slots[idx] = value;

        return new MeasureSlots(
                slots[0], slots[1], slots[2], slots[3],
                slots[4], slots[5], slots[6], slots[7]
        );
    }

    private static boolean hasAnyLyrics(List<NoteEntity> notes) {
        for (NoteEntity n : notes) {
            if (n.getLyric() != null && !n.getLyric().isBlank()) return true;
        }
        return false;
    }

    private static String defaultIfNull(String s) { return (s != null) ? s : ""; }
}
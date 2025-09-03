package com.github.sanhak.track.repository;

import com.github.sanhak.instrument.repository.InstrumentType;
import com.github.sanhak.track.controller.dto.response.TrackSummary;

import java.util.List;

public interface TrackRepositoryCustom {
    List<TrackSummary> findAllByInstrument(Long userId, InstrumentType instrument);
}

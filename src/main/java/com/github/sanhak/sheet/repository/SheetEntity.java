package com.github.sanhak.sheet.repository;

import com.github.sanhak.instrument.repository.InstrumentEntity;
import com.github.sanhak.track.repository.TrackEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sheets")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SheetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sheetId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instrument_id", nullable = false)
    private InstrumentEntity instrument;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "track_id", nullable = false)
    private TrackEntity track;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty", nullable = false)
    private SheetDifficulty difficulty;
}

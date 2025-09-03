package com.github.sanhak.track.repository;

import com.github.sanhak.instrument.repository.InstrumentType;
import com.github.sanhak.instrument.repository.QInstrumentEntity;
import com.github.sanhak.playresult.repository.QPlayResultEntity;
import com.github.sanhak.sheet.repository.QSheetEntity;
import com.github.sanhak.track.controller.dto.response.TrackSummary;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TrackRepositoryCustomImpl implements TrackRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    QTrackEntity track = QTrackEntity.trackEntity;
    QSheetEntity sheet = QSheetEntity.sheetEntity;
    QInstrumentEntity inst = QInstrumentEntity.instrumentEntity;
    QPlayResultEntity playResult = QPlayResultEntity.playResultEntity;

    @Override
    public List<TrackSummary> findAllByInstrument(Long userId, InstrumentType instrument) {

        return queryFactory.select(Projections.constructor(
                TrackSummary.class,
                track.id,
                track.title,
                inst.type,
                sheet.difficulty,
                track.durationMs,
                track.audioKey,
                ExpressionUtils.as(
                        JPAExpressions.select(playResult.accuracy.max())
                                .from(playResult)
                                .where(playResult.user.id.eq(userId), playResult.sheet.track.id.eq(track.id)), "record"
                )
        ))
        .from(track)
        .join(sheet).on(sheet.track.eq(track))
        .join(inst).on(sheet.instrument.eq(inst))
        .where(inst.type.eq(instrument))
        .fetch();

    }
}
package com.github.sanhak.sheet.repository;

import com.github.sanhak.instrument.repository.InstrumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SheetRepository extends JpaRepository<SheetEntity, Long> {
    @Query("""
       select s from SheetEntity s
       join fetch s.track t
       join fetch s.instrument i
       where t.id = :trackId and i.type = :instrument
    """)
    Optional<SheetEntity> findByTrackIdAndInstrument(
            @Param("trackId") Long trackId,
            @Param("instrument") InstrumentType instrument
    );
}

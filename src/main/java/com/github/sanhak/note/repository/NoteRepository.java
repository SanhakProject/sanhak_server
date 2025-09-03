package com.github.sanhak.note.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoteRepository extends JpaRepository<NoteEntity, Long> {

    @Query("""
              select n from NoteEntity n
              where n.sheet.id = :sheetId
              order by n.measureIdx asc, n.slotIdx asc
            """)
    List<NoteEntity> findBySheetIdOrderByMeasureIdxAscSlotIdxAsc(@Param("sheetId") Long sheetId);
}

package com.github.sanhak.note.repository;

import com.github.sanhak.global.entity.BaseTimeEntity;
import com.github.sanhak.sheet.repository.SheetEntity;
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
@Table(name = "notes")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoteEntity extends BaseTimeEntity {
    @Id
    @Column(name = "note_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sheet_id",  nullable = false)
    private SheetEntity sheet;

    @Column(name = "measure_idx", nullable = false)
    private Integer measureIdx;

    @Column(name = "slot_idx", nullable = false)
    private Integer slotIdx;

    @Column(name = "stroke", nullable = false)
    private String stroke;

    @Column(name = "lyric")
    private String lyric;

//    TODO: 연주의 세기(strength) 처리 로직 필요 시 활성화 및 구현
//    @Enumerated(EnumType.STRING)
//    @Column(name = "strength",  nullable = false)
//    private NoteStrength strength;
}

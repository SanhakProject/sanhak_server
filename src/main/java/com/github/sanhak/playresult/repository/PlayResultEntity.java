package com.github.sanhak.playresult.repository;

import com.github.sanhak.global.entity.BaseTimeEntity;
import com.github.sanhak.note.repository.NoteEntity;
import com.github.sanhak.sheet.repository.SheetEntity;
import com.github.sanhak.user.repository.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

import java.time.LocalDateTime;

@Entity
@Table(name = "playresults")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayResultEntity extends BaseTimeEntity {
    @Id
    @Column(name = "play_result_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sheet_id", nullable = false)
    private SheetEntity sheet;

    @Column(nullable = false)
    private Integer success;

    @Column(nullable = false)
    private Integer fail;

    @Column(name = "first_failed_measure_idx")
    private Integer firstFailedMeasureIdx;

    @Column(name = "first_failed_slot_idx")
    private Integer firstFailedSlotIdx;

    @Column(name = "max_combo", nullable = false)
    private Integer maxCombo;

    @Column(name = "accuracy", nullable = false)
    private Double accuracy; // 0.0 ~ 1.0

    @Column(name = "score", nullable = false)
    private Integer score;

    @Column(name = "played_at", nullable = false)
    private LocalDateTime playedAt;


}

package com.github.sanhak.judgment.repository;

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

@Entity
@Table(name = "judgments")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JudgmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long judgmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sheet_id", nullable = false)
    private SheetEntity sheet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "note_id",  nullable = false)
    private NoteEntity note;

    @Column(nullable = false)
    private Long success;

    @Column(nullable = false)
    private Long fail;

}

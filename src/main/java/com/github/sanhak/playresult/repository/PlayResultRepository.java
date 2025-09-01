package com.github.sanhak.playresult.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayResultRepository extends JpaRepository<PlayResultEntity, Long> {
    Page<PlayResultEntity> findByUserIdOrderByPlayedAtDesc(Long userId, Pageable pageable);
}

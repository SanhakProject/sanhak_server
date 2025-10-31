package com.github.sanhak.playresult.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlayResultRepository extends JpaRepository<PlayResultEntity, Long> {
    @Query("""
        select pr from PlayResultEntity pr
        join fetch pr.sheet s
        join fetch s.track t
        join fetch s.instrument i
        where pr.user.id = :userId
        order by pr.playedAt desc
    """)
    Page<PlayResultEntity> findByUserIdOrderByPlayedAtDesc(@Param("userId") Long userId, Pageable pageable);
}

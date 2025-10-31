package com.github.sanhak.playresult.repository;

import java.util.List;
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
        and pr.accuracy = (
            select max(pr2.accuracy)
            from PlayResultEntity pr2
            where pr2.user.id = :userId
            and pr2.sheet.id = pr.sheet.id
        )
        order by pr.playedAt desc
    """)
    List<PlayResultEntity> findByUserIdOrderByPlayedAtDesc(@Param("userId") Long userId);
}

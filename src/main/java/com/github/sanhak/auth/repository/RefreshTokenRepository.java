package com.github.sanhak.auth.repository;

import com.github.sanhak.user.repository.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

    Optional<RefreshTokenEntity> findByToken(String token);

    void deleteByUser(UserEntity user);

    void deleteByToken(String token);
}

package com.github.sanhak.user.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByPhoneNumberAndAuthType(String phoneNumer, AuthType authType);

    Optional<UserEntity> findByProviderId(Long aLong);
}

package com.github.sanhak.user.service;

import com.github.sanhak.user.exception.UserException;
import com.github.sanhak.user.exception.UserExceptionCode;
import com.github.sanhak.user.repository.UserEntity;
import com.github.sanhak.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserEntity findUserById(Long userId) {

        return userRepository.findById(userId) .orElseThrow(() -> new UserException(UserExceptionCode.USER_NOT_FOUND));
    }
}

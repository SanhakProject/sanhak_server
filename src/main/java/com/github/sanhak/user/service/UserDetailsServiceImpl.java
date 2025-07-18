package com.github.sanhak.user.service;

import com.github.sanhak.user.exception.UserException;
import com.github.sanhak.user.exception.UserExceptionCode;
import com.github.sanhak.user.repository.AuthType;
import com.github.sanhak.user.repository.UserEntity;
import com.github.sanhak.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String phoneNumer) {

        UserEntity user = userRepository.findByPhoneNumberAndAuthType(phoneNumer, AuthType.PHONE)
                .orElseThrow(() -> new UserException(UserExceptionCode.USER_NOT_FOUND));

        return new org.springframework.security.core.userdetails.User(
                user.getPhoneNumber(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}

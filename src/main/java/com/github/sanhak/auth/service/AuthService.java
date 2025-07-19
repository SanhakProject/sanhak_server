package com.github.sanhak.auth.service;

import com.github.sanhak.auth.controller.dto.request.LoginRequest;
import com.github.sanhak.auth.controller.dto.request.SignUpRequest;
import com.github.sanhak.auth.controller.dto.response.LoginResponse;
import com.github.sanhak.auth.exception.AuthException;
import com.github.sanhak.auth.exception.AuthExceptionCode;
import com.github.sanhak.global.security.jwt.JwtTokenProvider;
import com.github.sanhak.user.repository.AuthType;
import com.github.sanhak.user.repository.Role;
import com.github.sanhak.user.repository.UserEntity;
import com.github.sanhak.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void signUp(SignUpRequest request) {
        if (userRepository.findByPhoneNumberAndAuthType(request.getPhoneNumber(), AuthType.PHONE).isPresent()){
            throw new AuthException(AuthExceptionCode.ALREADY_REGISTERED);
        }

        UserEntity user = UserEntity.of(
                request.getPhoneNumber(),
                passwordEncoder.encode(request.getPassword()),
                request.getName(),
                AuthType.PHONE,
                Role.USER
        );

        userRepository.save(user);
    }

    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getPhoneNumber(), request.getPassword())
        );
        String accessToken = jwtTokenProvider.createAccessToken(authentication);

        return LoginResponse.of(accessToken);
    }

}

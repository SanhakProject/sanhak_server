package com.github.sanhak.user.repository;

import com.github.sanhak.global.entity.BaseTimeEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class UserEntity extends BaseTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long userId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthType authType;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @Nullable
    @Column(unique = true)
    private Long providerId;

    @NotNull
    private String name;

    @Nullable
    private String phoneNumber;

    @Nullable
    private String password;

    public static UserEntity of(String phoneNumber, String password, String name, AuthType authType, Role role) {
        UserEntity user = new UserEntity();
        user.phoneNumber = phoneNumber;
        user.password = password;
        user.name = name;
        user.authType = authType;
        user.role = role;
        return user;
    }
}

package com.github.sanhak.user.repository;

import com.github.sanhak.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthType authType;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(unique = true)
    private String providerId;

    @Column(nullable = false)
    private String name;

    private String phoneNumber;

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

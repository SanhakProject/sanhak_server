package com.github.sanhak.global.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app.jwt")
public class JwtProperty {
    private String key;
    private long accessTokenValidityInMilliseconds;
    //리프레쉬 토큰 사용시 주석 제거
    //private long refreshTokenValidityInMilliseconds;
}

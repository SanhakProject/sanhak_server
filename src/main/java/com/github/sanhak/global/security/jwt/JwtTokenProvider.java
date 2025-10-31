package com.github.sanhak.global.security.jwt;

import com.github.sanhak.global.property.JwtProperty;
import com.github.sanhak.global.security.UserAuthInfo;
import com.github.sanhak.global.security.jwt.exception.JwtAuthenticationException;
import com.github.sanhak.global.security.jwt.exception.JwtAuthenticationExceptionCode;
import com.github.sanhak.user.repository.UserEntity;
import com.github.sanhak.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperty jwtProperty;
    private final UserRepository userRepository;
    private Key key;
    private static final String PROVIDER_ID = "provider_id";
    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_PREFIX = "Bearer ";

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtProperty.getKey().getBytes());
    }

    public String createAccessToken(Authentication authentication) {
        return createToken(authentication, jwtProperty.getAccessTokenValidityInMilliseconds());
    }

    public String createRefreshToken(Authentication authentication) {
        return createToken(authentication, jwtProperty.getRefreshTokenValidityInMilliseconds());
    }

    public long getRefreshTokenValidityInMilliseconds() {
        return jwtProperty.getRefreshTokenValidityInMilliseconds();
    }

    private String createToken(Authentication authentication, long expireTime) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = System.currentTimeMillis();
        Date tokenExpiresIn = new Date(now + expireTime);

        return Jwts.builder()
                .claim(PROVIDER_ID,  authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(tokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);

        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new JwtAuthenticationException(JwtAuthenticationExceptionCode.MISSING_AUTHORITY);
        }

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        String providerId = claims.get("provider_id", String.class);

        // providerId로 사용자를 조회하여 UserAuthInfo 생성
        UserEntity user = userRepository.findByProviderId(providerId)
                .orElseThrow(() -> new JwtAuthenticationException(JwtAuthenticationExceptionCode.INVALID_TOKEN));

        UserAuthInfo userAuthInfo = new UserAuthInfo(user, user.getPhoneNumber(), null, null);

        return new UsernamePasswordAuthenticationToken(userAuthInfo, token, authorities);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

}

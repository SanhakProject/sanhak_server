package com.github.sanhak.global.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class LoggingFilter {

    public void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        long start  = System.currentTimeMillis();

        filterChain.doFilter(request, response);

        long end = System.currentTimeMillis();
        long elapsed = end - start;

        log.info("[{}] {} (ip={}) (status={}) {}ms",
                request.getMethod(),
                request.getRequestURI(),
                getIp(request),
                response.getStatus(),
                elapsed
        );
    }

    private String getIp(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");

        return xfHeader == null ? request.getRemoteAddr() : xfHeader.split(",")[0];
    }
}

package com.github.sanhak.global.external.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class S3Service {

    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // TODO: 추후 CDN 필요시 전환 고려
    //    @Value("${cloud.aws.s3.cdn-domain:}")
    //    private String cdnDomain;
    public String publicUrl(String key) {
        if (key == null) throw new IllegalArgumentException("S3 key must not be null");
        // 백슬래시는 슬래시로 정규화
        key = key.replace("\\", "/");

        // 선행 '/' 제거
        while (key.startsWith("/")) {
            key = key.substring(1);
        }

        // 세그먼트별 인코딩 (RFC 3986 방식)
        String safeKey = Arrays.stream(key.split("/", -1))
                .map(seg -> UriUtils.encodePathSegment(seg, StandardCharsets.UTF_8))
                .collect(Collectors.joining("/"));

        return "https://" + bucket + ".s3." + region + ".amazonaws.com/" + safeKey;
    }
}

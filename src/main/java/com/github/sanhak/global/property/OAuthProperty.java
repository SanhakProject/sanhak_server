package com.github.sanhak.global.property;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.oauth.callback")
public class OAuthProperty {

    @NotBlank
    private String success;

    @NotBlank
    private String failure;
}

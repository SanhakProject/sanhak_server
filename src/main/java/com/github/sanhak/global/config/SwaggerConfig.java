package com.github.sanhak.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "산학프로젝트 API", // TODO: 서비스명 정해지면 변경
                description = "디지털 사물놀이용 지휘 어플리케이션",
                version = "v1.0.0"
        ),
        servers = {
                @Server(url = "/api", description = "Default Server URL")
        }
)

@SecurityScheme(
        name = "JWT",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)

@Configuration
public class SwaggerConfig {}

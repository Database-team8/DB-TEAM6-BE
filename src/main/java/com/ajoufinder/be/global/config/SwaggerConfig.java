package com.ajoufinder.be.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addServersItem(new Server().url("https://ajoufinder.kr"))
                .info(new Info()
                        .title("AjouFinder API 명세서")                // ✅ 여기 변경
                        .version("v1.0")
                        .description("AjouFinder 서비스의 REST API 명세서입니다.")
                )
                .addSecurityItem(new SecurityRequirement().addList("sessionAuth"))
                .components(new Components().addSecuritySchemes("sessionAuth",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.COOKIE)
                                .name("JSESSIONID")  // 세션 쿠키 이름
                ));
    }
}

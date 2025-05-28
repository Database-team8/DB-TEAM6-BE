package com.ajoufinder.be.user.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "로그인 요청 DTO")
public record UserLoginRequest(

        @Schema(description = "아주대학교 이메일", example = "ina@ajou.ac.kr")
        String email,

        @Schema(description = "비밀번호", example = "securePassword123!")
        String password

) {}

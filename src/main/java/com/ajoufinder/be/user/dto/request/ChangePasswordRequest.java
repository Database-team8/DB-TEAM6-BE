package com.ajoufinder.be.user.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "비밀번호 요청 DTO")
public record ChangePasswordRequest(
        @NotBlank(message = "현재 비밀번호를 입력해주세요.")
        @Schema(description = "현재 비밀번호", example = "securePassword123!")
        String currentPassword,

        @NotBlank(message = "새 비밀번호를 입력해주세요.")
        @Schema(description = "새 비밀번호", example = "asdf1234")
        String newPassword
) {}
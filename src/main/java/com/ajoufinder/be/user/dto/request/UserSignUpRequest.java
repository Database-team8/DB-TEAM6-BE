package com.ajoufinder.be.user.dto.request;

import com.ajoufinder.be.user.domain.User;
import com.ajoufinder.be.user.domain.constant.Role;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "회원가입 요청 DTO")
public record UserSignUpRequest(

        @Schema(description = "이름", example = "정인아")
        @NotBlank String name,

        @Schema(description = "닉네임", example = "ina")
        @NotBlank String nickname,

        @Schema(description = "이메일", example = "ina@ajou.ac.kr")
        @Email @NotBlank String email,

        @Schema(description = "비밀번호", example = "securePassword123!")
        @NotBlank String password,

        @Schema(description = "자기소개", example = "백엔드 개발자입니다")
        String description,

        @Schema(description = "프로필 이미지 URL", example = "https://cdn.ajoufinder.com/images/profile.png")
        String profileImage,

        @Schema(description = "전화번호", example = "01012345678")
        String phoneNumber,

        @Schema(description = "회원 역할 (ADMIN or GUEST)", example = "GUEST")
        Role role
) {
    public User toEntity(String encodedPassword) {
        return User.builder()
                .name(this.name())
                .nickname(this.nickname())
                .email(this.email())
                .password(encodedPassword)
                .description(this.description())
                .profileImage(this.profileImage())
                .phoneNumber(this.phoneNumber())
                .role(this.role)
                .build();
    }
}

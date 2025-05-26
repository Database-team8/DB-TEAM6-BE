package com.ajoufinder.be.user.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "회원 정보 수정 요청 DTO")
public record UserUpdateRequest(

        @Schema(description = "프로필 이미지 URL", example = "https://cdn.ajoufinder.com/images/profile.png")
        String profileImage,

        @Schema(description = "이름", example = "정인아")
        String name,

        @Schema(description = "닉네임", example = "ina")
        String nickname,

        @Schema(description = "전화번호", example = "01012345678")
        String phoneNumber,

        @Schema(description = "자기소개", example = "백엔드 개발자입니다")
        String description

) {
}

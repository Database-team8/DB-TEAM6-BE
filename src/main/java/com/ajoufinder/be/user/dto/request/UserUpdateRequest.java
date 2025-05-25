package com.ajoufinder.be.user.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "회원 정보 수정 요청 DTO")
public record UserUpdateRequest(

        @Schema(description = "프로필 이미지 URL", example = "https://cdn.example.com/profile.jpg")
        String profileImage,

        @Schema(description = "이름", example = "홍길동")
        String name,

        @Schema(description = "닉네임", example = "gil-dong")
        String nickname,

        @Schema(description = "전화번호", example = "010-1234-5678")
        String phoneNumber,

        @Schema(description = "자기소개", example = "안녕하세요, 컴퓨터공학과 3학년 홍길동입니다.")
        String description

) {
}

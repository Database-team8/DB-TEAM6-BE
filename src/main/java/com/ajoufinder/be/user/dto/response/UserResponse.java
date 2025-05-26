package com.ajoufinder.be.user.dto.response;

import com.ajoufinder.be.user.domain.User;

public record UserResponse(
    Long userId,
    String name,
    String nickname,
    String profileImage
) {
    public static UserResponse from(User user) {
        return new UserResponse(
            user.getId(),
            user.getName(),
            user.getNickname(),
            user.getProfileImage()
        );
    }
}
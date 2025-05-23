package com.ajoufinder.be.user.dto;

import com.ajoufinder.be.user.domain.User;

public record UserSummary(
    Long userId,
    String name,
    String nickname,
    String profileImage
) {
    public static UserSummary from(User user) {
        return new UserSummary(
            user.getId(),
            user.getName(),
            user.getNickname(),
            user.getProfileImage()
        );
    }
}
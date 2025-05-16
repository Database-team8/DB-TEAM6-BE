package com.ajoufinder.be.user.domain;

import com.ajoufinder.be.global.domain.BaseTimeEntity;
import com.ajoufinder.be.user.domain.constant.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String nickname;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "profile_image", length = 255)
    private String profileImage;

    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;


    @Builder
    public User(String name, String nickname, String email, String password, String description,
                String profileImage, String phoneNumber, Role role) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.description = description;
        this.profileImage = profileImage;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }
}

package com.ajoufinder.be.user.service;

import com.ajoufinder.be.user.domain.User;
import com.ajoufinder.be.user.dto.request.UserSignUpRequest;
import com.ajoufinder.be.user.dto.request.UserUpdateRequest;
import com.ajoufinder.be.user.dto.response.UserInfoResponse;
import com.ajoufinder.be.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long signUp(UserSignUpRequest request) {
        validateEmail(request.email());
        String password=passwordEncoder.encode(request.password());
        User user=request.toEntity(password);
        userRepository.save(user);
        return user.getId();
    }

    public void validateEmail(String email) {
        if (email == null || !email.endsWith("@ajou.ac.kr")) {
            throw new IllegalArgumentException("아주대학교 이메일(@ajou.ac.kr)만 사용할 수 있습니다.");
        }

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("이미 등록된 이메일입니다.");
        }
    }

    @Transactional
    public void updateProfile(User user, UserUpdateRequest request) {
        user.updateProfile(request);
        userRepository.save(user);
    }

    public UserInfoResponse getUserProfile(User user) {
        return UserInfoResponse.from(user);
    }
}


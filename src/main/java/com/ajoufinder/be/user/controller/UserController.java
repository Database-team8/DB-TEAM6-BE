package com.ajoufinder.be.user.controller;

import com.ajoufinder.be.global.api_response.ApiResponse;
import com.ajoufinder.be.user.dto.UserSignUpRequest;
import com.ajoufinder.be.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "유저 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Operation(
            summary = "회원가입",
            description = """
        새로운 사용자를 등록합니다. 아래 필드는 필수입니다:
        
        - name: 이름
        - nickname: 닉네임
        - email: 아주대 이메일 (xxx@ajou.ac.kr)
        - password: 비밀번호
        - role: 회원 역할 (ADMIN, GUEST 중 하나)
        """
    )
    @PostMapping("/signup")
    public ApiResponse<Long> signUp(@Valid @RequestBody UserSignUpRequest request) {
        Long userId = userService.signUp(request);
        return ApiResponse.onSuccess(userId);
    }
}

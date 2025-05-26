package com.ajoufinder.be.user.controller;

import com.ajoufinder.be.global.api_response.ApiResponse;
import com.ajoufinder.be.global.domain.UserPrincipal;
import com.ajoufinder.be.user.dto.request.UserSignUpRequest;
import com.ajoufinder.be.user.dto.request.UserUpdateRequest;
import com.ajoufinder.be.user.dto.response.UserInfoResponse;
import com.ajoufinder.be.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @Operation(
            summary = "내 계정 정보 수정",
            description = """
    사용자 본인의 계정 정보를 수정합니다.
    - 수정 가능 항목: 이름, 닉네임, 전화번호, 자기소개
    """
    )
    @PutMapping("/profile")
    public ApiResponse<String> updateUserInfo(
            @Valid @RequestBody UserUpdateRequest request,
            @AuthenticationPrincipal UserPrincipal principal) {

        userService.updateProfile(principal.getUser(), request);
        return ApiResponse.onSuccess("사용자 정보가 성공적으로 수정되었습니다.");
    }

    @Operation(
            summary = "내 정보 조회",
            description = "로그인한 사용자의 프로필 정보를 조회합니다."
    )
    @GetMapping("/profile")
    public ApiResponse<UserInfoResponse> getMyProfile(
            @AuthenticationPrincipal UserPrincipal principal) {
        UserInfoResponse response = userService.getUserProfile(principal.getUser());
        return ApiResponse.onSuccess(response);
    }

}

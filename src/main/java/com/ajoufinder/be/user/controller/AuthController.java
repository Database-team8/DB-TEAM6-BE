package com.ajoufinder.be.user.controller;

import com.ajoufinder.be.global.api_response.ApiResponse;
import com.ajoufinder.be.user.dto.request.UserLoginRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth", description = "인증 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    @Operation(
            summary = "로그인",
            description = """
        이메일과 비밀번호로 로그인합니다.

        ✅ 응답으로 반환되는 `result` 값은 현재 로그인 세션의 `sessionId`입니다.  
        Swagger에서 이 세션을 이용해 인증이 필요한 API를 호출하려면, 아래와 같이 `Cookie` 헤더에 포함시켜야 합니다:

        ```
        Cookie: JSESSIONID={sessionId}
        ```

        🔐 세션 기반 인증을 사용하며, 로그인 후 받은 sessionId는 브라우저 또는 Swagger UI에 쿠키로 설정되지 않기 때문에,  
        이후 인증이 필요한 요청 시 위 형식으로 직접 전달해야 합니다.
        """
    )
    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody UserLoginRequest request, HttpServletRequest httpRequest) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(request.email(), request.password());

        Authentication authentication = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 세션 생성 (기본적으로 SecurityContextPersistenceFilter가 처리함)
        HttpSession session = httpRequest.getSession(true);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());
        String sessionId = session.getId();
        return ApiResponse.onSuccess(sessionId);
    }

    @Operation(
            summary = "로그아웃"
    )
    @PostMapping("/logout")
    public ApiResponse<String> logout(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        request.logout(); // 세션 종료
        return ApiResponse.onSuccess("로그아웃 성공");
    }
}


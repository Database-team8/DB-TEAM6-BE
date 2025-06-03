package com.ajoufinder.be.condition.controller;


import com.ajoufinder.be.condition.dto.request.ConditionCreateRequest;
import com.ajoufinder.be.condition.dto.response.ConditionResponse;
import com.ajoufinder.be.condition.service.ConditionService;
import com.ajoufinder.be.global.api_response.ApiResponse;
import com.ajoufinder.be.global.domain.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Condition", description = "조건 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/conditions")

public class ConditionController {
    private final ConditionService conditionService;

    @Operation(
        summary = "조건 생성",
        description = """
        조건을 생성합니다. 다음 필드는 필수입니다:
        - item_type_id: 조건을 적용할 물품 타입 id
        - location_id: 조건을 적용할 위치 id
        """
    )
    @PostMapping
    public ApiResponse<Long> createCondition(@AuthenticationPrincipal UserPrincipal principal, @Valid @RequestBody ConditionCreateRequest request) {
        Long conditionId = conditionService.createCondition(principal.getUser(), request);
        return ApiResponse.onSuccess(conditionId);
    }

    @Operation(summary = "사용자의 모든 조건 조회")
    @GetMapping
    public ApiResponse<List<ConditionResponse>> getMyConditions(@AuthenticationPrincipal UserPrincipal principal) {
        List<ConditionResponse> conditions = conditionService.getConditionsByUser(principal.getUser());
        return ApiResponse.onSuccess(conditions);
    }

    @Operation(summary = "조건 삭제")
    @DeleteMapping("/{conditionId}")
    public ApiResponse<String> deleteCondition(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable Long conditionId) {
        conditionService.deleteCondition(principal.getUser(), conditionId);
        return ApiResponse.onSuccess("삭제 완료");
    }
}

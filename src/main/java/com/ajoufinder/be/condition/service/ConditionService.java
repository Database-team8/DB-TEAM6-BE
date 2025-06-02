package com.ajoufinder.be.condition.service;

import com.ajoufinder.be.condition.domain.Condition;
import com.ajoufinder.be.condition.dto.request.ConditionCreateRequest;
import com.ajoufinder.be.condition.dto.response.ConditionResponse;
import com.ajoufinder.be.condition.repository.ConditionRepository;
import com.ajoufinder.be.global.api_response.exception.GeneralException;
import com.ajoufinder.be.global.api_response.status.ErrorStatus;
import com.ajoufinder.be.item_type.domain.ItemType;
import com.ajoufinder.be.item_type.service.ItemTypeService;
import com.ajoufinder.be.location.domain.Location;
import com.ajoufinder.be.location.service.LocationService;
import com.ajoufinder.be.user.domain.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConditionService {
    private final LocationService locationService;
    private final ItemTypeService itemTypeService;
    private final ConditionRepository conditionRepository;

    @Transactional
    public Long createCondition(User user, ConditionCreateRequest request) {
        Location location=locationService.getLocationByIdOrThrow(request.locationId());
        ItemType itemType = itemTypeService.getItemTypeByIdOrThrow(request.itemTypeId());
        Condition condition=request.toEntity(itemType, location, user);
        return conditionRepository.save(condition).getId();
    }

    public List<ConditionResponse> getConditionsByUser(User user) {
        List<Condition> conditions = conditionRepository.findAllByUser(user);
        return conditions.stream()
                .map(ConditionResponse::from)
                .toList();
    }

    @Transactional
    public void deleteCondition(User user, Long conditionId) {
        Condition condition = conditionRepository.findById(conditionId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.NOT_FOUND,"존재하지 않는 조건입니다."));
        if (!condition.getUser().getId().equals(user.getId())) {
            throw new GeneralException(ErrorStatus.UNAUTHORIZED);
        }
        conditionRepository.delete(condition);
    }
}

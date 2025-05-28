package com.ajoufinder.be.location.dto.response;

import com.ajoufinder.be.location.domain.Location;
import com.ajoufinder.be.location.domain.constant.LocationName;

public record LocationResponse(
    Long locationId,
    LocationName locationName
) {
    public static LocationResponse from(Location location) {
        return new LocationResponse(
            location.getId(),
            location.getLocationName()
        );
    }
}

package com.ajoufinder.be.location.dto;

import com.ajoufinder.be.location.domain.Location;
import com.ajoufinder.be.location.domain.constant.LocationName;

public record LocationSummary(
    Long locationId,
    LocationName locationName
) {
    public static LocationSummary from(Location location) {
        return new LocationSummary(
            location.getId(),
            location.getLocationName()
        );
    }
}

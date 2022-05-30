package com.migros.couriertracking.domain.model.aggregate.value.converter;

import com.migros.couriertracking.domain.model.aggregate.value.Location;
import com.migros.couriertracking.infrastructure.persistence.entity.LocationEntity;
import org.springframework.stereotype.Component;

@Component
public class LocationConverter {
    public LocationEntity convert(final Location location) {
        return location == null ? null : LocationEntity.builder()
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .build();
    }
}

package com.migros.couriertracking.domain.model.aggregate.value.builder;

import com.migros.couriertracking.domain.model.aggregate.value.Location;
import com.migros.couriertracking.domain.model.aggregate.value.dto.LoadLocationDto;
import com.migros.couriertracking.infrastructure.persistence.entity.LocationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocationBuilder {
    public Location build(final LocationEntity locationEntity) {
        return locationEntity == null ? null :  Location.load(LoadLocationDto.builder()
                .latitude(locationEntity.getLatitude())
                .longitude(locationEntity.getLongitude())
                .build());
    }
}

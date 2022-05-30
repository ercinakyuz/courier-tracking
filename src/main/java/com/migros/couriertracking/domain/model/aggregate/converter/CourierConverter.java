package com.migros.couriertracking.domain.model.aggregate.converter;

import com.migros.couriertracking.domain.model.aggregate.Courier;
import com.migros.couriertracking.domain.model.aggregate.value.converter.LocationConverter;
import com.migros.couriertracking.infrastructure.persistence.entity.CourierEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourierConverter {

    private final LocationConverter locationConverter;

    public CourierEntity convert(final Courier courier){
        return CourierEntity.builder()
                .id(courier.getId())
                .firstname(courier.getFirstname())
                .lastname(courier.getLastname())
                .totalTravelDistance(courier.getTotalTravelDistance())
                .location(locationConverter.convert(courier.getLocation()))
                .createdAt(courier.getCreatedAt())
                .createdBy(courier.getCreatedBy())
                .modifiedAt(courier.getModifiedAt())
                .modifiedBy(courier.getModifiedBy())
                .build();
    }
}

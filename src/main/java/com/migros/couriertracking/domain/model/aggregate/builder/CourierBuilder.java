package com.migros.couriertracking.domain.model.aggregate.builder;

import com.migros.couriertracking.domain.exception.DomainException;
import com.migros.couriertracking.domain.model.aggregate.Courier;
import com.migros.couriertracking.domain.model.aggregate.dto.LoadCourierDto;
import com.migros.couriertracking.domain.model.aggregate.value.builder.LocationBuilder;
import com.migros.couriertracking.infrastructure.persistence.repository.CourierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

import static com.migros.couriertracking.domain.model.aggregate.error.CourierError.COURIER_NOT_FOUND;
import static com.migros.couriertracking.infrastructure.exception.ExceptionState.UNPROCESSABLE;

@Component
@RequiredArgsConstructor
public class CourierBuilder {

    private final CourierRepository courierRepository;
    private final LocationBuilder locationBuilder;

    public Optional<Courier> buildOptional(final UUID id) {
        return courierRepository.findById(id).map(courierEntity -> Courier.load(LoadCourierDto.builder()
                .id(courierEntity.getId())
                .firstname(courierEntity.getFirstname())
                .lastname(courierEntity.getLastname())
                .totalTravelDistance(courierEntity.getTotalTravelDistance())
                .location(locationBuilder.build(courierEntity.getLocation()))
                .createdAt(courierEntity.getCreatedAt())
                .createdBy(courierEntity.getCreatedBy())
                .modifiedAt(courierEntity.getModifiedAt())
                .modifiedBy(courierEntity.getModifiedBy())
                .build()));
    }

    public Courier build(final UUID id) {
        return buildOptional(id).orElseThrow(() -> new DomainException(UNPROCESSABLE, COURIER_NOT_FOUND));
    }
}

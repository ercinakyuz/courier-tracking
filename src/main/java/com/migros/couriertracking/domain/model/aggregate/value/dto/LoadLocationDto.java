package com.migros.couriertracking.domain.model.aggregate.value.dto;

import com.migros.couriertracking.domain.exception.DomainException;
import com.migros.couriertracking.infrastructure.exception.ExceptionState;
import com.migros.couriertracking.infrastructure.exception.guard.Guard;
import lombok.Builder;
import lombok.Data;

import static com.migros.couriertracking.domain.model.aggregate.value.error.LocationError.*;

@Data
@Builder
public class LoadLocationDto {

    private double latitude;

    private double longitude;

    public void validate(final ExceptionState exceptionState) {
        Guard.that(latitude < -90 || latitude > 90, new DomainException(exceptionState, INVALID_LATITUDE));
        Guard.that(longitude < -180 || longitude > 180, new DomainException(exceptionState, INVALID_LONGITUDE));
    }
}

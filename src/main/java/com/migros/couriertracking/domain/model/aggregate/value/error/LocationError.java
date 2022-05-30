package com.migros.couriertracking.domain.model.aggregate.value.error;

import com.migros.couriertracking.domain.exception.DomainError;

public final class LocationError {
    private LocationError(){}
    public static DomainError INVALID_LATITUDE = DomainError.builder().code("LOCDE-1").message("Invalid latitude").build();
    public static DomainError INVALID_LONGITUDE = DomainError.builder().code("LOCDE-2").message("Invalid longitude").build();
}

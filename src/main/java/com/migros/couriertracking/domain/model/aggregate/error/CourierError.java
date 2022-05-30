package com.migros.couriertracking.domain.model.aggregate.error;

import com.migros.couriertracking.domain.exception.DomainError;

public final class CourierError {
    private CourierError(){}
    public static final DomainError INVALID_ID = DomainError.builder().code("COURDE-1").message("Invalid id").build();

    public static final DomainError INVALID_CREATED_AT = DomainError.builder().code("COURDE-2").message("Invalid created at").build();

    public static final DomainError INVALID_CREATED_BY = DomainError.builder().code("COURDE-3").message("Invalid created by").build();
    public static final DomainError INVALID_FIRSTNAME = DomainError.builder().code("COURDE-4").message("Invalid first name").build();
    public static final DomainError INVALID_LASTNAME = DomainError.builder().code("COURDE-5").message("Invalid last name").build();
    public static final DomainError INVALID_TOTAL_TRAVEL_DISTANCE = DomainError.builder().code("COURDE-6").message("Invalid total travel distance").build();
    public static final DomainError COURIER_NOT_FOUND = DomainError.builder().code("COURDE-7").message("Courier not found").build();

}

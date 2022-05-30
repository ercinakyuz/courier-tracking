package com.migros.couriertracking.domain.model.aggregate.dto;

import com.migros.couriertracking.domain.exception.DomainException;
import com.migros.couriertracking.infrastructure.exception.guard.Guard;
import lombok.Builder;
import lombok.Data;

import static com.migros.couriertracking.domain.model.aggregate.error.CourierError.*;
import static com.migros.couriertracking.infrastructure.exception.ExceptionState.INVALID;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Data
@Builder
public class CreateCourierDto {

    private String firstname;

    private String lastname;

    public void validate() {
        Guard.that(isBlank(firstname), new DomainException(INVALID, INVALID_FIRSTNAME));
        Guard.that(isBlank(lastname), new DomainException(INVALID, INVALID_LASTNAME));
    }

}

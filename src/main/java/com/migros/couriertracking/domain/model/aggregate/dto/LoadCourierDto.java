package com.migros.couriertracking.domain.model.aggregate.dto;

import com.migros.couriertracking.domain.exception.DomainException;
import com.migros.couriertracking.domain.model.aggregate.value.Location;
import com.migros.couriertracking.infrastructure.exception.guard.Guard;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

import static com.migros.couriertracking.domain.model.aggregate.error.CourierError.*;
import static com.migros.couriertracking.infrastructure.exception.ExceptionState.UNPROCESSABLE;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Data
@SuperBuilder
public class LoadCourierDto extends AbstractLoadAggregateDto<UUID> {

    private String firstname;
    private String lastname;
    private double totalTravelDistance;

    private Location location;

    public void validate() {
        Guard.that(id == null, new DomainException(UNPROCESSABLE, INVALID_ID));
        Guard.that(createdAt == null, new DomainException(UNPROCESSABLE, INVALID_CREATED_AT));
        Guard.that(isBlank(createdBy), new DomainException(UNPROCESSABLE, INVALID_CREATED_BY));
        Guard.that(isBlank(firstname), new DomainException(UNPROCESSABLE, INVALID_FIRSTNAME));
        Guard.that(isBlank(lastname), new DomainException(UNPROCESSABLE, INVALID_LASTNAME));
        Guard.that(totalTravelDistance < 0, new DomainException(UNPROCESSABLE, INVALID_TOTAL_TRAVEL_DISTANCE));
    }
}

package com.migros.couriertracking.domain.model.aggregate.event;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class CourierLocationChanged extends AbstractEvent {
    UUID id;
}

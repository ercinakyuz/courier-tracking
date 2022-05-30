package com.migros.couriertracking.domain.model.aggregate.value;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class Store{

    UUID id;

    String name;

    Location location;
}

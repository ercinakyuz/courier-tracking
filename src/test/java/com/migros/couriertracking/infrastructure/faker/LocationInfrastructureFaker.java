package com.migros.couriertracking.infrastructure.faker;

import com.migros.couriertracking.infrastructure.persistence.entity.LocationEntity;

public class LocationInfrastructureFaker extends AbstractFaker {
    public double latitude() {
        return faker.number().numberBetween(-90, 90);
    }
    public double longitude() {
        return faker.number().numberBetween(-180, 180);
    }

    public LocationEntity locationEntity() {
        return LocationEntity.builder()
                .latitude(latitude())
                .longitude(longitude())
                .build();
    }
}

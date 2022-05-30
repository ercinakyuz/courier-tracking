package com.migros.couriertracking.infrastructure.faker;

import com.migros.couriertracking.infrastructure.persistence.entity.CourierEntity;

public class CourierInfrastructureFaker extends AbstractInfrastructureFaker {

    private final LocationInfrastructureFaker locationInfrastructureFaker;

    public CourierInfrastructureFaker() {
        locationInfrastructureFaker = new LocationInfrastructureFaker();
    }


    public CourierEntity createdCourierEntity() {
        final CourierEntity courierEntity = CourierEntity.builder()
                .firstname(firstname())
                .lastname(lastname())
                .build();
        loadAbstractPropertiesForCreation(courierEntity);
        return courierEntity;
    }

    public CourierEntity modifiedCourierEntity() {
        final CourierEntity courierEntity = CourierEntity.builder()
                .firstname(firstname())
                .lastname(lastname())
                .totalTravelDistance(totalTravelDistance())
                .location(locationInfrastructureFaker.locationEntity())
                .build();
        loadAbstractPropertiesForModification(courierEntity);
        return courierEntity;
    }

    public String firstname() {
        return faker.name().firstName();
    }

    public String lastname() {
        return faker.name().lastName();
    }

    public double totalTravelDistance() {
        return faker.number().numberBetween(1, 1000);
    }
}

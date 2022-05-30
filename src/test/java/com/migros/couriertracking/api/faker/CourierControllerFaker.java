package com.migros.couriertracking.api.faker;

import com.migros.couriertracking.api.model.request.ChangeCourierLocationRequest;
import com.migros.couriertracking.api.model.request.RegisterCourierRequest;
import com.migros.couriertracking.infrastructure.faker.AbstractFaker;
import com.migros.couriertracking.infrastructure.faker.CourierInfrastructureFaker;
import com.migros.couriertracking.infrastructure.faker.LocationInfrastructureFaker;

public class CourierControllerFaker extends AbstractFaker {

    private final CourierInfrastructureFaker courierInfrastructureFaker;

    private final LocationInfrastructureFaker locationInfrastructureFaker;

    public CourierControllerFaker() {
        courierInfrastructureFaker = new CourierInfrastructureFaker();
        locationInfrastructureFaker = new LocationInfrastructureFaker();
    }

    public RegisterCourierRequest registerCourierRequest() {
        return RegisterCourierRequest.builder()
                .firstname(courierInfrastructureFaker.firstname())
                .lastname(courierInfrastructureFaker.lastname())
                .build();
    }

    public ChangeCourierLocationRequest changeCourierLocationRequest() {
        return ChangeCourierLocationRequest.builder()
                .latitude(locationInfrastructureFaker.latitude())
                .longitude(locationInfrastructureFaker.longitude())
                .build();
    }
}

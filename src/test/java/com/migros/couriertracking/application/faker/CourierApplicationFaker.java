package com.migros.couriertracking.application.faker;

import com.migros.couriertracking.application.contract.CourierContract;
import com.migros.couriertracking.application.usecase.getcourier.command.GetCourierCommand;
import com.migros.couriertracking.infrastructure.faker.AbstractFaker;
import com.migros.couriertracking.infrastructure.faker.CourierInfrastructureFaker;

public class CourierApplicationFaker extends AbstractFaker {

    private CourierInfrastructureFaker courierInfrastructureFaker;
    private LocationApplicationFaker locationApplicationFaker;

    public CourierApplicationFaker() {
        courierInfrastructureFaker = new CourierInfrastructureFaker();
        locationApplicationFaker = new LocationApplicationFaker();
    }

    public GetCourierCommand getCourierCommand() {
        return GetCourierCommand.builder()
                .id(courierInfrastructureFaker.id())
                .build();
    }

    public CourierContract courierContract() {
        return CourierContract.builder()
                .id(courierInfrastructureFaker.id())
                .firstname(courierInfrastructureFaker.firstname())
                .lastname(courierInfrastructureFaker.lastname())
                .totalTravelDistance(courierInfrastructureFaker.totalTravelDistance())
                .location(locationApplicationFaker.locationContract())
                .build();
    }
}


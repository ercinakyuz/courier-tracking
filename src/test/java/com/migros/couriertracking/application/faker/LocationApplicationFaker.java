package com.migros.couriertracking.application.faker;

import com.migros.couriertracking.application.contract.LocationContract;
import com.migros.couriertracking.infrastructure.faker.AbstractFaker;
import com.migros.couriertracking.infrastructure.faker.LocationInfrastructureFaker;

public class LocationApplicationFaker extends AbstractFaker {

    private LocationInfrastructureFaker locationInfrastructureFaker;

    public LocationApplicationFaker() {
        locationInfrastructureFaker = new LocationInfrastructureFaker();
    }


    public LocationContract locationContract() {
        return LocationContract.builder()
                .latitude(locationInfrastructureFaker.latitude())
                .longitude(locationInfrastructureFaker.longitude())
                .build();
    }
}

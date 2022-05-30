package com.migros.couriertracking.domain.faker;

import com.migros.couriertracking.domain.model.aggregate.value.Location;
import com.migros.couriertracking.domain.model.aggregate.value.dto.LoadLocationDto;
import com.migros.couriertracking.infrastructure.faker.AbstractFaker;
import com.migros.couriertracking.infrastructure.faker.LocationInfrastructureFaker;

public class LocationFaker extends AbstractFaker {
    private LocationInfrastructureFaker locationInfrastructureFaker;

    public LocationFaker() {
        locationInfrastructureFaker = new LocationInfrastructureFaker();
    }


    public Location location() {
        return Location.load(loadDto());
    }

    public LoadLocationDto loadDto() {
        return LoadLocationDto.builder()
                .latitude(locationInfrastructureFaker.latitude())
                .longitude(locationInfrastructureFaker.longitude())
                .build();
    }
}

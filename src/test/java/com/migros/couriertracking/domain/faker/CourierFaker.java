package com.migros.couriertracking.domain.faker;

import com.migros.couriertracking.domain.model.aggregate.Courier;
import com.migros.couriertracking.domain.model.aggregate.dto.LoadCourierDto;
import com.migros.couriertracking.domain.model.aggregate.event.CourierLocationChanged;
import com.migros.couriertracking.domain.model.aggregate.value.Location;
import com.migros.couriertracking.infrastructure.faker.CourierInfrastructureFaker;

import java.util.UUID;

public class CourierFaker extends AbstractAggregateFaker {

    private CourierInfrastructureFaker courierInfrastructureFaker;
    private LocationFaker locationFaker;

    public CourierFaker() {
        courierInfrastructureFaker = new CourierInfrastructureFaker();
        locationFaker = new LocationFaker();
    }

    public CourierLocationChanged courierLocationChanged(final UUID id) {
        return CourierLocationChanged.builder()
                .id(id)
                .build();
    }

    public Courier courier() {
        return Courier.load(loadDto());
    }
    public LoadCourierDto loadDto(final Location location){
        final LoadCourierDto loadDto = LoadCourierDto.builder()
                .firstname(courierInfrastructureFaker.firstname())
                .lastname(courierInfrastructureFaker.lastname())
                .totalTravelDistance(courierInfrastructureFaker.totalTravelDistance())
                .location(location)
                .build();

        loadAbstractPropertiesForCreation(loadDto);

        return loadDto;
    }
    public LoadCourierDto loadDto(){
        final LoadCourierDto loadDto = LoadCourierDto.builder()
                .firstname(courierInfrastructureFaker.firstname())
                .lastname(courierInfrastructureFaker.lastname())
                .totalTravelDistance(courierInfrastructureFaker.totalTravelDistance())
                .location(locationFaker.location())
                .build();

        loadAbstractPropertiesForCreation(loadDto);

        return loadDto;
    }
}

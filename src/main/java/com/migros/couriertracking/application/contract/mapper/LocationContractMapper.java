package com.migros.couriertracking.application.contract.mapper;

import com.migros.couriertracking.application.contract.LocationContract;
import com.migros.couriertracking.domain.model.aggregate.value.Location;
import org.springframework.stereotype.Component;

@Component
public class LocationContractMapper {
    public LocationContract map(final Location location) {
        return location == null ? null : LocationContract.builder()
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .build();
    }
}

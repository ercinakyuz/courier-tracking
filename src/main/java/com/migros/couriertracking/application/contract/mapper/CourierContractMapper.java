package com.migros.couriertracking.application.contract.mapper;

import com.migros.couriertracking.application.contract.CourierContract;
import com.migros.couriertracking.domain.model.aggregate.Courier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourierContractMapper {
    private final LocationContractMapper locationContractMapper;

    public CourierContract map(final Courier courier){
        return CourierContract.builder()
                .id(courier.getId())
                .firstname(courier.getFirstname())
                .lastname(courier.getLastname())
                .totalTravelDistance(courier.getTotalTravelDistance())
                .location(locationContractMapper.map(courier.getLocation()))
                .build();
    }
}

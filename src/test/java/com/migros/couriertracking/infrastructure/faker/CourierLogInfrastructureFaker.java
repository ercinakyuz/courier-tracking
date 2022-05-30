package com.migros.couriertracking.infrastructure.faker;

import com.migros.couriertracking.infrastructure.persistence.entity.CourierLogEntity;

public class CourierLogInfrastructureFaker extends AbstractInfrastructureFaker {

    public CourierLogEntity courierLogEntity(){
        final CourierLogEntity courierLogEntity = CourierLogEntity.builder()
                .storeId(id())
                .courierId(id())
                .build();

        loadAbstractPropertiesForCreation(courierLogEntity);

        return courierLogEntity;
    }
}

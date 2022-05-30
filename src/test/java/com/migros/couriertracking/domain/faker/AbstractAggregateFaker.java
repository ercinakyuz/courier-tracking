package com.migros.couriertracking.domain.faker;

import com.migros.couriertracking.domain.model.aggregate.dto.AbstractLoadAggregateDto;
import com.migros.couriertracking.infrastructure.faker.AbstractInfrastructureFaker;

import java.util.UUID;

public abstract class AbstractAggregateFaker extends AbstractInfrastructureFaker {
    protected void loadAbstractPropertiesForCreation(final AbstractLoadAggregateDto<UUID> loadDto) {
        loadDto.setId(id());
        loadDto.setCreatedAt(createdAt());
        loadDto.setCreatedBy(createdBy());
    }

    protected void loadAbstractPropertiesForModification(final AbstractLoadAggregateDto<UUID> loadDto) {
        loadAbstractPropertiesForCreation(loadDto);
        loadDto.setModifiedAt(modifiedAt());
        loadDto.setModifiedBy(modifiedBy());
    }
}

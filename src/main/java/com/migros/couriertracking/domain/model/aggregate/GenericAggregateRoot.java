package com.migros.couriertracking.domain.model.aggregate;

import com.migros.couriertracking.domain.model.aggregate.dto.AbstractLoadAggregateDto;
import lombok.Getter;

@Getter
public abstract class GenericAggregateRoot<TAggregate extends AggregateRoot, TId, TState> extends AggregateRoot {
    protected TId id;
    protected TState state;

    protected TAggregate changeState(final TState state) {
        this.state = state;
        return (TAggregate) this.applyEvents();
    }

    protected TAggregate loadAbstractProperties(final AbstractLoadAggregateDto<TId> loadDto){
        id = loadDto.getId();
        createdAt = loadDto.getCreatedAt();
        createdBy = loadDto.getCreatedBy();
        modifiedAt = loadDto.getModifiedAt();
        modifiedBy = loadDto.getModifiedBy();
        return (TAggregate) this;
    }
}

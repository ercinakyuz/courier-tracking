package com.migros.couriertracking.domain.model.aggregate.ofwork;


import com.migros.couriertracking.domain.model.aggregate.AggregateRoot;

public interface AggregateOfWork<TAggregate extends AggregateRoot> {

    TAggregate insert(TAggregate aggregate);

    TAggregate update(TAggregate aggregate);

    TAggregate delete(TAggregate aggregate);
}

package com.migros.couriertracking.domain.model.aggregate.ofwork;

import com.migros.couriertracking.domain.model.aggregate.Courier;
import com.migros.couriertracking.domain.model.aggregate.converter.CourierConverter;
import com.migros.couriertracking.infrastructure.persistence.repository.CourierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourierOfWork implements AggregateOfWork<Courier>{
    private final CourierRepository courierRepository;
    private final CourierConverter courierConverter;

    @Override
    public Courier insert(final Courier courier) {
        courierRepository.save(courierConverter.convert(courier));
        return courier;
    }

    @Override
    public Courier update(final Courier courier) {
        if (courier.hasAnyChanges())
            courierRepository.save(courierConverter.convert(courier));
        return courier;
    }

    @Override
    public Courier delete(final Courier courier) {
        return null;
    }

}

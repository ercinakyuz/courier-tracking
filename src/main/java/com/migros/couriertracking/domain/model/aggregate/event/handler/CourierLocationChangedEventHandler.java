package com.migros.couriertracking.domain.model.aggregate.event.handler;

import com.migros.couriertracking.domain.configuration.CourierLogConfiguration;
import com.migros.couriertracking.domain.model.aggregate.Courier;
import com.migros.couriertracking.domain.model.aggregate.builder.CourierBuilder;
import com.migros.couriertracking.domain.model.aggregate.event.CourierLocationChanged;
import com.migros.couriertracking.domain.model.aggregate.value.builder.StoreBuilder;
import com.migros.couriertracking.infrastructure.persistence.entity.CourierLogEntity;
import com.migros.couriertracking.infrastructure.persistence.repository.CourierLogRepository;
import com.migros.couriertracking.infrastructure.provider.InstantProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CourierLocationChangedEventHandler implements ApplicationListener<CourierLocationChanged> {
    private final CourierBuilder courierBuilder;
    private final StoreBuilder storeBuilder;
    private final CourierLogConfiguration courierLogConfiguration;
    private final CourierLogRepository courierLogRepository;
    private final InstantProvider instantProvider;

    @Override
    public void onApplicationEvent(final CourierLocationChanged event) {
        final Courier courier = courierBuilder.build(event.getId());
        storeBuilder.buildAll().forEach(store -> {
            double distanceInMeters = store.getLocation().distanceInMeters(courier.getLocation());
            if (distanceInMeters <= courierLogConfiguration.getAvailabilityDistanceInMeters()) {
                final boolean courierLogExists = courierLogRepository.existsByCourierIdAndStoreIdAndCreatedAtAfter(
                        courier.getId(), store.getId(), instantProvider.now().minus(courierLogConfiguration.getDuration()));
                if (!courierLogExists)
                    courierLogRepository.save(CourierLogEntity.builder()
                            .id(UUID.randomUUID())
                            .courierId(courier.getId())
                            .storeId(store.getId())
                            .createdAt(instantProvider.now())
                            .createdBy("System")
                            .build());
            }
        });
    }
}

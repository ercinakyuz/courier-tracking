package com.migros.couriertracking.domain.faker;

import com.migros.couriertracking.infrastructure.faker.AbstractFaker;

import java.time.Duration;

public class CourierLogConfigurationFaker extends AbstractFaker {

    public Duration duration() {
        return Duration.ofSeconds(faker.number().numberBetween(1, 3));
    }

    public int availabilityDistanceInMeters() {
        return faker.number().numberBetween(100, 200);
    }
}

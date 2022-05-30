package com.migros.couriertracking.domain.faker;

import com.migros.couriertracking.domain.model.aggregate.value.Store;
import com.migros.couriertracking.infrastructure.faker.AbstractFaker;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StoreFaker extends AbstractFaker {

    private LocationFaker locationFaker;

    public StoreFaker() {
        locationFaker = new LocationFaker();
    }

    public List<Store> stores() {
        return IntStream.range(0, faker.number().numberBetween(1,3)).mapToObj(i -> store()).collect(Collectors.toList());
    }

    public Store store(){
        return Store.builder()
                .id(UUID.randomUUID())
                .name(faker.company().name())
                .location(locationFaker.location())
                .build();
    }
}

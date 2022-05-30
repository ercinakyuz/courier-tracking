package com.migros.couriertracking.domain;

import com.migros.couriertracking.domain.faker.CourierFaker;
import com.migros.couriertracking.domain.faker.LocationFaker;
import com.migros.couriertracking.domain.faker.StoreFaker;
import com.migros.couriertracking.infrastructure.AbstractTest;
import org.junit.jupiter.api.BeforeAll;

public abstract class AbstractDomainTest extends AbstractTest {

    protected static CourierFaker courierFaker;
    protected static StoreFaker storeFaker;
    protected static LocationFaker locationFaker;

    @BeforeAll
    public static void beforeAllDomain() {
        courierFaker = new CourierFaker();
        storeFaker = new StoreFaker();
        locationFaker = new LocationFaker();
    }
}

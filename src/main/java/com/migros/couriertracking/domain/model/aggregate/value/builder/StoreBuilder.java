package com.migros.couriertracking.domain.model.aggregate.value.builder;

import com.migros.couriertracking.domain.model.aggregate.value.Store;
import com.migros.couriertracking.domain.model.aggregate.value.Location;
import com.migros.couriertracking.domain.model.aggregate.value.dto.LoadLocationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class StoreBuilder {
    private static final List<Store> STORES = List.of(
            create("Ataşehir MMM Migros", 40.9923307, 29.1244229),
            create("Novada MMM Migros", 40.986106, 29.1161293),
            create("Beylikdüzü 5M Migros", 41.0066851, 28.6552262),
            create("Ortaköy MMM Migros", 41.055783, 29.0210292),
            create("Caddebostan MMM Migros", 40.9632463, 29.0630908)
    );
    public List<Store> buildAll() {
        return STORES;
    }

    private static Store create(final String name, final double latitude, final double longitude) {
        return Store.builder()
                .id(UUID.randomUUID())
                .name(name)
                .location(Location.create(LoadLocationDto.builder()
                        .latitude(latitude)
                        .longitude(longitude)
                        .build()))
                .build();
    }
}

package com.migros.couriertracking.domain.model.aggregate.event.handler;

import com.migros.couriertracking.domain.AbstractDomainTest;
import com.migros.couriertracking.domain.configuration.CourierLogConfiguration;
import com.migros.couriertracking.domain.faker.CourierLogConfigurationFaker;
import com.migros.couriertracking.domain.model.aggregate.Courier;
import com.migros.couriertracking.domain.model.aggregate.builder.CourierBuilder;
import com.migros.couriertracking.domain.model.aggregate.dto.LoadCourierDto;
import com.migros.couriertracking.domain.model.aggregate.event.CourierLocationChanged;
import com.migros.couriertracking.domain.model.aggregate.value.Store;
import com.migros.couriertracking.domain.model.aggregate.value.builder.StoreBuilder;
import com.migros.couriertracking.infrastructure.persistence.entity.CourierLogEntity;
import com.migros.couriertracking.infrastructure.persistence.repository.CourierLogRepository;
import com.migros.couriertracking.infrastructure.provider.InstantProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CourierLocationChangedEventHandlerTest extends AbstractDomainTest {

    private static CourierLogConfigurationFaker courierLogConfigurationFaker;
    @Mock
    private CourierBuilder courierBuilder;
    @Mock
    private StoreBuilder storeBuilder;
    @Mock
    private CourierLogConfiguration courierLogConfiguration;
    @Mock
    private CourierLogRepository courierLogRepository;
    @Mock
    private InstantProvider instantProvider;

    @Captor
    private ArgumentCaptor<CourierLogEntity> courierLogEntityArgumentCaptor;

    private CourierLocationChangedEventHandler courierLocationChangedEventHandler;

    @BeforeAll
    public static void beforeAll() {
        courierLogConfigurationFaker = new CourierLogConfigurationFaker();
    }

    @BeforeEach
    public void beforeEach() {
        courierLocationChangedEventHandler = new CourierLocationChangedEventHandler(courierBuilder, storeBuilder, courierLogConfiguration, courierLogRepository, instantProvider);
    }


    @Test
    public void should_log_when_courier_near_to_store_out_of_log_duration() {
        //given
        final Instant now = Instant.now();
        final Store store = storeFaker.store();
        final List<Store> stores = List.of(store);
        final LoadCourierDto loadCourierDto = courierFaker.loadDto();
        loadCourierDto.setLocation(store.getLocation());
        final Courier courier = Courier.load(loadCourierDto);
        final CourierLocationChanged courierLocationChanged = courierFaker.courierLocationChanged(courier.getId());
        final UUID id = courierLocationChanged.getId();
        final Duration courierLogDuration = courierLogConfigurationFaker.duration();
        final int courierLogAvailabilityDistanceInMeters = courierLogConfigurationFaker.availabilityDistanceInMeters();

        //when
        when(courierBuilder.build(id)).thenReturn(courier);
        when(storeBuilder.buildAll()).thenReturn(stores);
        when(courierLogConfiguration.getDuration()).thenReturn(courierLogDuration);
        when(courierLogConfiguration.getAvailabilityDistanceInMeters()).thenReturn(courierLogAvailabilityDistanceInMeters);
        when(courierLogRepository.existsByCourierIdAndStoreIdAndCreatedAtAfter(courier.getId(), store.getId(), now.minus(courierLogConfiguration.getDuration()))).thenReturn(false);
        when(instantProvider.now()).thenReturn(now);

        courierLocationChangedEventHandler.onApplicationEvent(courierLocationChanged);

        //then
        verify(courierLogRepository).save(courierLogEntityArgumentCaptor.capture());

        final CourierLogEntity courierLogEntity = courierLogEntityArgumentCaptor.getValue();

        assertThat(courierLogEntity).isNotNull();
        assertThat(courierLogEntity.getCourierId()).isEqualTo(courier.getId());
        assertThat(courierLogEntity.getStoreId()).isEqualTo(store.getId());
        assertThat(courierLogEntity.getCreatedAt()).isEqualTo(now);
    }

    @Test
    public void should_not_log_when_courier_near_to_store_in_log_duration() {
        //given
        final Instant now = Instant.now();
        final Store store = storeFaker.store();
        final List<Store> stores = List.of(store);
        final LoadCourierDto loadCourierDto = courierFaker.loadDto();
        loadCourierDto.setLocation(store.getLocation());
        final Courier courier = Courier.load(loadCourierDto);
        final CourierLocationChanged courierLocationChanged = courierFaker.courierLocationChanged(courier.getId());
        final UUID id = courierLocationChanged.getId();
        final Duration courierLogDuration = courierLogConfigurationFaker.duration();
        final int courierLogAvailabilityDistanceInMeters = courierLogConfigurationFaker.availabilityDistanceInMeters();

        //when
        when(courierBuilder.build(id)).thenReturn(courier);
        when(storeBuilder.buildAll()).thenReturn(stores);
        when(courierLogConfiguration.getDuration()).thenReturn(courierLogDuration);
        when(courierLogConfiguration.getAvailabilityDistanceInMeters()).thenReturn(courierLogAvailabilityDistanceInMeters);
        when(courierLogRepository.existsByCourierIdAndStoreIdAndCreatedAtAfter(courier.getId(), store.getId(), now.minus(courierLogConfiguration.getDuration()))).thenReturn(true);
        when(instantProvider.now()).thenReturn(now);

        courierLocationChangedEventHandler.onApplicationEvent(courierLocationChanged);

        //then
        verifyNoMoreInteractions(courierLogRepository);
    }

    @Test
    public void should_not_log_when_courier_away_from_store() {
        //given
        final Store store = storeFaker.store();
        final List<Store> stores = List.of(store);
        final Courier courier = courierFaker.courier();
        final CourierLocationChanged courierLocationChanged = courierFaker.courierLocationChanged(courier.getId());
        final UUID id = courierLocationChanged.getId();
        final int courierLogAvailabilityDistanceInMeters = courierLogConfigurationFaker.availabilityDistanceInMeters();

        //when
        when(courierBuilder.build(id)).thenReturn(courier);
        when(storeBuilder.buildAll()).thenReturn(stores);
        when(courierLogConfiguration.getAvailabilityDistanceInMeters()).thenReturn(courierLogAvailabilityDistanceInMeters);

        courierLocationChangedEventHandler.onApplicationEvent(courierLocationChanged);

        //then
        verifyNoMoreInteractions(courierLogConfiguration, courierLogRepository);
    }
}

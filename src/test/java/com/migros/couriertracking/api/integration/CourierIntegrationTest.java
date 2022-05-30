package com.migros.couriertracking.api.integration;


import com.migros.couriertracking.api.faker.CourierControllerFaker;
import com.migros.couriertracking.api.model.request.ChangeCourierLocationRequest;
import com.migros.couriertracking.api.model.request.RegisterCourierRequest;
import com.migros.couriertracking.api.model.response.GetCourierResponse;
import com.migros.couriertracking.api.model.response.RegisterCourierResponse;
import com.migros.couriertracking.application.contract.CourierContract;
import com.migros.couriertracking.application.contract.LocationContract;
import com.migros.couriertracking.domain.model.aggregate.value.Location;
import com.migros.couriertracking.domain.model.aggregate.value.Store;
import com.migros.couriertracking.domain.model.aggregate.value.builder.StoreBuilder;
import com.migros.couriertracking.infrastructure.faker.CourierInfrastructureFaker;
import com.migros.couriertracking.infrastructure.faker.CourierLogInfrastructureFaker;
import com.migros.couriertracking.infrastructure.persistence.entity.CourierEntity;
import com.migros.couriertracking.infrastructure.persistence.entity.CourierLogEntity;
import com.migros.couriertracking.infrastructure.persistence.entity.LocationEntity;
import com.migros.couriertracking.infrastructure.persistence.repository.CourierLogRepository;
import com.migros.couriertracking.infrastructure.persistence.repository.CourierRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;
import java.util.UUID;

import static com.migros.couriertracking.api.model.url.ApiUrlPath.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CourierIntegrationTest extends AbstractIntegrationTest {

    private static CourierControllerFaker courierControllerFaker;

    private static CourierInfrastructureFaker courierInfrastructureFaker;

    private static CourierLogInfrastructureFaker courierLogInfrastructureFaker;

    @Autowired
    private CourierRepository courierRepository;

    @Autowired
    private CourierLogRepository courierLogRepository;

    @Autowired
    private StoreBuilder storeBuilder;


    @BeforeAll
    public static void beforeAll() {
        courierControllerFaker = new CourierControllerFaker();
        courierInfrastructureFaker = new CourierInfrastructureFaker();
        courierLogInfrastructureFaker = new CourierLogInfrastructureFaker();
    }

    @AfterEach
    public void afterEach() {
        courierRepository.deleteAll();
    }

    @Test
    public void should_register() throws Exception {
        // given
        final RegisterCourierRequest request = courierControllerFaker.registerCourierRequest();

        // when
        final ResultActions resultActions = mockMvc.perform(post(COURIER_REGISTRATION_URL)
                .contentType(APPLICATION_JSON)
                .content(objectMapperContext.objectToByteArray(request))
        );

        // then
        resultActions.andExpect(status().isCreated());

        final RegisterCourierResponse response = objectMapperContext.mvcResultToResponse(RegisterCourierResponse.class, resultActions.andReturn());
        assertThat(response).isNotNull();
        final UUID id = response.getResult();
        assertThat(id).isNotNull();

        final Optional<CourierEntity> optionalCourierEntity = courierRepository.findById(id);
        assertThat(optionalCourierEntity).isPresent();

        final CourierEntity courierEntity = optionalCourierEntity.get();
        assertThat(courierEntity.getFirstname()).isEqualTo(request.getFirstname());
        assertThat(courierEntity.getLastname()).isEqualTo(request.getLastname());
        assertThat(courierEntity.getTotalTravelDistance()).isZero();
        assertThat(courierEntity.getLocation()).isNull();
        assertThat(courierEntity.getCreatedAt()).isNotNull();
        assertThat(courierEntity.getCreatedBy()).isEqualTo(String.format("%s %s", request.getFirstname(), request.getLastname()));
    }

    @Test
    public void should_get() throws Exception {
        // given
        final CourierEntity courierEntity = courierInfrastructureFaker.modifiedCourierEntity();
        courierRepository.save(courierEntity);
        final UUID id = courierEntity.getId();
        final LocationEntity locationEntity = courierEntity.getLocation();
        // when
        final ResultActions resultActions = mockMvc.perform(get(COURIER_GET_BY_ID_URL, id)
                .contentType(APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isOk());

        final GetCourierResponse response = objectMapperContext.mvcResultToResponse(GetCourierResponse.class, resultActions.andReturn());
        assertThat(response).isNotNull();
        final CourierContract contract = response.getResult();
        assertThat(contract).isNotNull();

        assertThat(contract.getId()).isEqualTo(id);
        assertThat(contract.getFirstname()).isEqualTo(courierEntity.getFirstname());
        assertThat(contract.getLastname()).isEqualTo(courierEntity.getLastname());
        assertThat(contract.getTotalTravelDistance()).isEqualTo(courierEntity.getTotalTravelDistance());

        final LocationContract locationContract = contract.getLocation();
        assertThat(locationContract).isNotNull();

        assertThat(locationContract.getLatitude()).isEqualTo(locationEntity.getLatitude());
        assertThat(locationContract.getLongitude()).isEqualTo(locationEntity.getLongitude());
    }

    @Test
    public void should_init_location() throws Exception {
        // given
        final CourierEntity courierEntity = courierInfrastructureFaker.createdCourierEntity();
        courierRepository.save(courierEntity);
        final UUID id = courierEntity.getId();
        final ChangeCourierLocationRequest request = courierControllerFaker.changeCourierLocationRequest();

        // when
        final ResultActions resultActions = mockMvc.perform(patch(COURIER_CHANGE_LOCATION_URL, id)
                .contentType(APPLICATION_JSON)
                .content(objectMapperContext.objectToByteArray(request))
        );

        // then
        resultActions.andExpect(status().isOk());

        final Optional<CourierEntity> optionalModifiedCourierEntity = courierRepository.findById(id);
        assertThat(optionalModifiedCourierEntity).isPresent();

        final CourierEntity modifiedCourierEntity = optionalModifiedCourierEntity.get();
        assertThat(modifiedCourierEntity.getFirstname()).isEqualTo(courierEntity.getFirstname());
        assertThat(modifiedCourierEntity.getLastname()).isEqualTo(courierEntity.getLastname());
        assertThat(modifiedCourierEntity.getTotalTravelDistance()).isZero();
        assertThat(modifiedCourierEntity.getCreatedAt().toEpochMilli()).isEqualTo(courierEntity.getCreatedAt().toEpochMilli());
        assertThat(modifiedCourierEntity.getCreatedBy()).isEqualTo(courierEntity.getCreatedBy());
        assertThat(modifiedCourierEntity.getModifiedAt()).isNotNull();
        assertThat(modifiedCourierEntity.getModifiedBy()).isEqualTo(String.format("%s %s", courierEntity.getFirstname(), courierEntity.getLastname()));

        final LocationEntity modifiedLocationEntity = modifiedCourierEntity.getLocation();
        assertThat(modifiedLocationEntity).isNotNull();

        assertThat(modifiedLocationEntity.getLatitude()).isEqualTo(request.getLatitude());
        assertThat(modifiedLocationEntity.getLongitude()).isEqualTo(request.getLongitude());
    }

    @Test
    public void should_change_location() throws Exception {
        // given
        final CourierEntity courierEntity = courierInfrastructureFaker.modifiedCourierEntity();
        courierRepository.save(courierEntity);
        final UUID id = courierEntity.getId();
        final ChangeCourierLocationRequest request = courierControllerFaker.changeCourierLocationRequest();

        // when
        final ResultActions resultActions = mockMvc.perform(patch(COURIER_CHANGE_LOCATION_URL, id)
                .contentType(APPLICATION_JSON)
                .content(objectMapperContext.objectToByteArray(request))
        );

        // then
        resultActions.andExpect(status().isOk());

        final Optional<CourierEntity> optionalModifiedCourierEntity = courierRepository.findById(id);
        assertThat(optionalModifiedCourierEntity).isPresent();

        final CourierEntity modifiedCourierEntity = optionalModifiedCourierEntity.get();
        assertThat(modifiedCourierEntity.getFirstname()).isEqualTo(courierEntity.getFirstname());
        assertThat(modifiedCourierEntity.getLastname()).isEqualTo(courierEntity.getLastname());
        assertThat(modifiedCourierEntity.getTotalTravelDistance()).isGreaterThan(0);
        assertThat(modifiedCourierEntity.getCreatedAt().toEpochMilli()).isEqualTo(courierEntity.getCreatedAt().toEpochMilli());
        assertThat(modifiedCourierEntity.getCreatedBy()).isEqualTo(courierEntity.getCreatedBy());
        assertThat(modifiedCourierEntity.getModifiedAt()).isAfter(courierEntity.getModifiedAt());
        assertThat(modifiedCourierEntity.getModifiedBy()).isEqualTo(String.format("%s %s", courierEntity.getFirstname(), courierEntity.getLastname()));

        final LocationEntity modifiedLocationEntity = modifiedCourierEntity.getLocation();
        assertThat(modifiedLocationEntity).isNotNull();

        assertThat(modifiedLocationEntity.getLatitude()).isEqualTo(request.getLatitude());
        assertThat(modifiedLocationEntity.getLongitude()).isEqualTo(request.getLongitude());
    }

    @Test
    public void should_change_location_without_logging() throws Exception {
        // given
        final CourierEntity courierEntity = courierInfrastructureFaker.modifiedCourierEntity();
        courierRepository.save(courierEntity);
        final UUID id = courierEntity.getId();
        final Store firstStore = storeBuilder.buildAll().get(0);
        final Location locationOfFirstStore = firstStore.getLocation();
        final ChangeCourierLocationRequest request = courierControllerFaker.changeCourierLocationRequest();
        request.setLatitude(locationOfFirstStore.getLatitude());
        request.setLongitude(locationOfFirstStore.getLongitude());

        final CourierLogEntity courierLogEntity = courierLogInfrastructureFaker.courierLogEntity();
        courierLogEntity.setCourierId(id);
        courierLogEntity.setStoreId(firstStore.getId());
        courierLogRepository.save(courierLogEntity);

        // when
        final ResultActions resultActions = mockMvc.perform(patch(COURIER_CHANGE_LOCATION_URL, id)
                .contentType(APPLICATION_JSON)
                .content(objectMapperContext.objectToByteArray(request))
        );

        // then
        resultActions.andExpect(status().isOk());

        final Optional<CourierEntity> optionalModifiedCourierEntity = courierRepository.findById(id);
        assertThat(optionalModifiedCourierEntity).isPresent();

        final CourierEntity modifiedCourierEntity = optionalModifiedCourierEntity.get();
        assertThat(modifiedCourierEntity.getFirstname()).isEqualTo(courierEntity.getFirstname());
        assertThat(modifiedCourierEntity.getLastname()).isEqualTo(courierEntity.getLastname());
        assertThat(modifiedCourierEntity.getTotalTravelDistance()).isGreaterThan(0);
        assertThat(modifiedCourierEntity.getCreatedAt().toEpochMilli()).isEqualTo(courierEntity.getCreatedAt().toEpochMilli());
        assertThat(modifiedCourierEntity.getCreatedBy()).isEqualTo(courierEntity.getCreatedBy());
        assertThat(modifiedCourierEntity.getModifiedAt()).isAfter(courierEntity.getModifiedAt());
        assertThat(modifiedCourierEntity.getModifiedBy()).isEqualTo(String.format("%s %s", courierEntity.getFirstname(), courierEntity.getLastname()));

        final LocationEntity modifiedLocationEntity = modifiedCourierEntity.getLocation();
        assertThat(modifiedLocationEntity).isNotNull();

        assertThat(modifiedLocationEntity.getLatitude()).isEqualTo(request.getLatitude());
        assertThat(modifiedLocationEntity.getLongitude()).isEqualTo(request.getLongitude());


    }
}
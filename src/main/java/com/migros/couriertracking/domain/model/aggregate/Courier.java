package com.migros.couriertracking.domain.model.aggregate;

import com.migros.couriertracking.domain.model.aggregate.dto.CreateCourierDto;
import com.migros.couriertracking.domain.model.aggregate.dto.LoadCourierDto;
import com.migros.couriertracking.domain.model.aggregate.event.CourierLocationChanged;
import com.migros.couriertracking.domain.model.aggregate.state.CourierState;
import com.migros.couriertracking.domain.model.aggregate.value.Location;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

import static com.migros.couriertracking.domain.model.aggregate.state.CourierState.*;
import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder(access = PRIVATE)
public class Courier extends GenericAggregateRoot<Courier, UUID, CourierState> {

    private String firstname;

    private String lastname;
    private double totalTravelDistance;
    private Location location;

    public static Courier load(final LoadCourierDto loadDto) {
        loadDto.validate();
        final Courier courier = Courier.builder()
                .firstname(loadDto.getFirstname())
                .lastname(loadDto.getLastname())
                .location(loadDto.getLocation())
                .totalTravelDistance(loadDto.getTotalTravelDistance())
                .build();
        return courier.loadAbstractProperties(loadDto).changeState(LOADED);
    }

    public static Courier create(final CreateCourierDto createDto) {
        createDto.validate();
        final Courier courier = Courier.builder()
                .firstname(createDto.getFirstname())
                .lastname(createDto.getLastname())
                .build();

        courier.id = UUID.randomUUID();
        courier.createdAt = Instant.now();
        courier.createdBy = String.format("%s %s", courier.getFirstname(), courier.getLastname());
        return courier.changeState(CREATED);
    }
    public Courier changeLocation(final Location location) {
        if (this.location == null)
            changeState(LOCATION_CHANGED);
        else {
            final double distanceInMeters = calculateDistanceInMeters(location);
            if (distanceInMeters > 0) {
                updateTotalTravelDistance(distanceInMeters);
                changeState(LOCATION_CHANGED);
            }
        }
        this.location = location;
        return logForCourierModification();
    }
    public boolean hasAnyChanges() {
        return !LOADED.equals(state);
    }
    @Override
    protected Courier applyEvents() {
        if (state.equals(LOCATION_CHANGED))
            events.add(CourierLocationChanged.builder().id(id).build());
        return this;
    }
    private Courier updateTotalTravelDistance(final double distanceInMeters) {
        totalTravelDistance += distanceInMeters;
        return this;
    }
    private double calculateDistanceInMeters(final Location nextLocation) {
        return location != null ? location.distanceInMeters(nextLocation) : 0;
    }
    private Courier logForCourierModification() {
        modifiedAt = Instant.now();
        modifiedBy = String.format("%s %s", getFirstname(), getLastname());
        return this;
    }
}

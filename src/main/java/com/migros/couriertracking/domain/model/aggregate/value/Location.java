package com.migros.couriertracking.domain.model.aggregate.value;

import com.migros.couriertracking.domain.model.aggregate.value.dto.LoadLocationDto;
import lombok.Builder;
import lombok.Value;

import static com.migros.couriertracking.infrastructure.exception.ExceptionState.INVALID;
import static com.migros.couriertracking.infrastructure.exception.ExceptionState.UNPROCESSABLE;
import static java.lang.Math.*;
import static lombok.AccessLevel.PRIVATE;

@Value
@Builder(access = PRIVATE)
public class Location {
    private static final double EARTH_RADIUS_IN_METERS = 6371000;
    double latitude;
    double longitude;

    public static Location load(final LoadLocationDto loadDto) {
        loadDto.validate(UNPROCESSABLE);
        return Location.builder()
                .latitude(loadDto.getLatitude())
                .longitude(loadDto.getLongitude())
                .build();
    }

    public static Location create(final LoadLocationDto loadDto) {
        loadDto.validate(INVALID);
        return Location.builder()
                .latitude(loadDto.getLatitude())
                .longitude(loadDto.getLongitude())
                .build();
    }
    public double distanceInMeters(final Location anotherLocation) {

        var differenceOfLatitude = toRadiansAsDifference(anotherLocation.getLatitude(), latitude);
        var differenceOfLongitude = toRadiansAsDifference(anotherLocation.getLongitude(), longitude);

        var radiansOfLatitude1 = toRadians(latitude);
        var radiansOfLatitude2 = toRadians(anotherLocation.getLatitude());

        var a = pow(sin(differenceOfLatitude / 2), 2) +
                pow(sin(differenceOfLongitude / 2), 2) * cos(radiansOfLatitude1) * cos(radiansOfLatitude2);
        var c = 2 * atan2(sqrt(a), sqrt(1 - a));
        return EARTH_RADIUS_IN_METERS * c;
    }

    private double toRadiansAsDifference(final double degree1, final double degree2) {
        return toRadians(degree2 - degree1);
    }

    private double toRadians(final double degree) {
        return degree * PI / 180;
    }

}

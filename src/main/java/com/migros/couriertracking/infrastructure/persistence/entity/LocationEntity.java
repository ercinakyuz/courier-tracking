package com.migros.couriertracking.infrastructure.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class LocationEntity {

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;
}


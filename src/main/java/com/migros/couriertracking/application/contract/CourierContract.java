package com.migros.couriertracking.application.contract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourierContract {

    private UUID id;

    private String firstname;

    private String lastname;

    private double totalTravelDistance;

    private LocationContract location;
}

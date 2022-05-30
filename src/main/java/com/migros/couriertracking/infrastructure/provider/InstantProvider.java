package com.migros.couriertracking.infrastructure.provider;

import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class InstantProvider {

    public Instant now(){
        return Instant.now();
    }
}

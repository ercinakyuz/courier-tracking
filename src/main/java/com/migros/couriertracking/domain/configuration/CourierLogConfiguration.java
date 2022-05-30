package com.migros.couriertracking.domain.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Data
@Configuration
@ConfigurationProperties(prefix = "courier-log")
public class CourierLogConfiguration {

    private Duration duration;

    private int availabilityDistanceInMeters;
}

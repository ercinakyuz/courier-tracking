package com.migros.couriertracking.infrastructure.persistence.repository;

import com.migros.couriertracking.infrastructure.persistence.entity.CourierLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.UUID;

public interface CourierLogRepository extends JpaRepository<CourierLogEntity, UUID> {
    boolean existsByCourierIdAndStoreIdAndCreatedAtAfter(UUID courierId, UUID storeId, Instant createdAt);
}

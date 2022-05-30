package com.migros.couriertracking.infrastructure.persistence.repository;

import com.migros.couriertracking.infrastructure.persistence.entity.CourierEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourierRepository extends JpaRepository<CourierEntity, UUID> {
}

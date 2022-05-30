package com.migros.couriertracking.infrastructure.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "courier_log", indexes = @Index(name = "ix_courierId_storeId_createdAt", columnList = "courier_id, store_id, created_at"))
public class CourierLogEntity extends AbstractEntity {

    @Column(name = "courier_id", nullable = false)
    @Type(type = "uuid-char")
    private UUID courierId;

    @Column(name = "store_id", nullable = false)
    @Type(type = "uuid-char")
    private UUID storeId;
}

package com.migros.couriertracking.domain.model.aggregate.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Data
@SuperBuilder
public class AbstractLoadAggregateDto<TId> {

    protected TId id;

    protected Instant createdAt;

    protected Instant modifiedAt;

    protected String createdBy;

    protected String modifiedBy;

}


package com.migros.couriertracking.domain.exception;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DomainError {

    private String code;

    private String message;

    private String userMessage;
}

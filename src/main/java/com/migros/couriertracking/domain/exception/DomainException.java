package com.migros.couriertracking.domain.exception;


import com.migros.couriertracking.infrastructure.exception.CoreException;
import com.migros.couriertracking.infrastructure.exception.ExceptionState;
import lombok.Getter;

@Getter
public class DomainException extends CoreException {
    public DomainException(final ExceptionState state, final DomainError domainError) {
        this(state, domainError.getCode(), domainError.getMessage(), domainError.getUserMessage());
    }
    public DomainException(final ExceptionState state, final String code, final String message, final String userMessage) {
        super(state, code, message, userMessage);
    }
}

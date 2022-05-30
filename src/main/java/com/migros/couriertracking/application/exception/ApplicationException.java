package com.migros.couriertracking.application.exception;

import com.migros.couriertracking.infrastructure.exception.CoreException;
import com.migros.couriertracking.infrastructure.exception.ExceptionState;
import lombok.Getter;

@Getter
public class ApplicationException extends CoreException {

    public ApplicationException(final ExceptionState state, final String code, final String message) {
        this(state, code, message, null);
    }

    public ApplicationException(final ExceptionState state, final String code, final String message, final String userMessage) {
        super(state, code, message, userMessage);
    }
}
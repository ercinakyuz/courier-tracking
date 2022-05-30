package com.migros.couriertracking.application.usecase.getcourier.command.handler.error;

import com.migros.couriertracking.application.exception.ApplicationException;
import com.migros.couriertracking.infrastructure.exception.ExceptionState;

public final class GetCourierCommandHandlerError {

    private GetCourierCommandHandlerError() {
    }

    public static final ApplicationException COURIER_NOT_FOUND = new ApplicationException(ExceptionState.DOES_NOT_EXIST, "GCOURCHE-1", "Courier not found");
}

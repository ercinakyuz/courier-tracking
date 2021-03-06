package com.migros.couriertracking.api.httphandler;


import com.migros.couriertracking.api.error.contract.builder.ErrorContractBuilder;
import com.migros.couriertracking.api.error.response.ErrorResponse;
import com.migros.couriertracking.infrastructure.error.GenericError;
import com.migros.couriertracking.infrastructure.exception.CoreException;
import com.migros.couriertracking.infrastructure.exception.ExceptionState;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Map;

import static com.migros.couriertracking.infrastructure.exception.ExceptionState.*;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@RequiredArgsConstructor
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String ERROR_SPLITTER = ";";
    private static final String CONSTRAINT_VIOLATION_ERROR_SPLITTER = ": ";
    private final ErrorContractBuilder errorContractBuilder;
    private static final Map<ExceptionState, HttpStatus> RESPONSE_ENTITY_STATUS_MAP = Map.of(
            UNKNOWN, INTERNAL_SERVER_ERROR,
            INVALID, BAD_REQUEST,
            DOES_NOT_EXIST, NOT_FOUND,
            ALREADY_EXIST, CONFLICT,
            AUTHORIZATION_FAILED, UNAUTHORIZED,
            PRE_CONDITION_FAILED, PRECONDITION_FAILED,
            PRE_CONDITION_REQUIRED, PRECONDITION_REQUIRED,
            UNPROCESSABLE, UNPROCESSABLE_ENTITY
    );

    @Override
    public ResponseEntity handleMethodArgumentNotValid(final MethodArgumentNotValidException exception, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

        final String errorCode = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        logger.warn(String.format("Request validation error occurred with code: %s, message: %s ", errorCode, exception.getMessage()), exception);
        return buildErrorResponseEntity(INVALID, errorCode);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handleConstraintViolationException(final ConstraintViolationException exception) {

        final String[] splittedError = exception.getMessage().split(CONSTRAINT_VIOLATION_ERROR_SPLITTER)[1].split(ERROR_SPLITTER);
        final String errorCode = splittedError[0];
        logger.warn(String.format("Request validation error occurred with code: %s, message: %s ", errorCode, exception.getMessage()), exception);
        return buildErrorResponseEntity(INVALID, errorCode);
    }

    @ExceptionHandler(CoreException.class)
    public ResponseEntity handleCoreException(final CoreException coreException) {
        logger.error(String.format("Core exception occured with code: %s, message: %s ", coreException.getCode(), coreException.getMessage()), coreException);
        return buildErrorResponseEntityFromCoreException(coreException);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(final Exception exception) {
        logger.error(String.format("An exception occured with code: %s, message: %s ", UNKNOWN, exception.getMessage()), exception);
        return buildErrorResponseEntity(UNKNOWN, GenericError.CODE);
    }

    private ResponseEntity buildErrorResponseEntityFromCoreException(final CoreException coreException) {
        return ResponseEntity
                .status(RESPONSE_ENTITY_STATUS_MAP.get(coreException.getState()))
                .body(ErrorResponse.builder()
                        .error(errorContractBuilder.build(coreException))
                        .build());
    }

    private ResponseEntity buildErrorResponseEntity(final ExceptionState exceptionState, final String code) {
        return ResponseEntity.status(RESPONSE_ENTITY_STATUS_MAP.get(exceptionState)).body(ErrorResponse.builder()
                .error(errorContractBuilder.build(code))
                .build());
    }
}


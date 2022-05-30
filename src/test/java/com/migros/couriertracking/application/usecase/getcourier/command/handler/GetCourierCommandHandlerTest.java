package com.migros.couriertracking.application.usecase.getcourier.command.handler;

import com.migros.couriertracking.application.contract.CourierContract;
import com.migros.couriertracking.application.contract.mapper.CourierContractMapper;
import com.migros.couriertracking.application.exception.ApplicationException;
import com.migros.couriertracking.application.faker.CourierApplicationFaker;
import com.migros.couriertracking.application.usecase.getcourier.command.GetCourierCommand;
import com.migros.couriertracking.application.usecase.getcourier.command.result.GetCourierCommandResult;
import com.migros.couriertracking.domain.faker.CourierFaker;
import com.migros.couriertracking.domain.model.aggregate.Courier;
import com.migros.couriertracking.domain.model.aggregate.builder.CourierBuilder;
import com.migros.couriertracking.infrastructure.AbstractTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;
import java.util.UUID;

import static com.migros.couriertracking.application.usecase.getcourier.command.handler.error.GetCourierCommandHandlerError.COURIER_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.when;

public class GetCourierCommandHandlerTest extends AbstractTest {
    private static CourierApplicationFaker courierApplicationFaker;
    private static CourierFaker courierFaker;
    @Mock
    private CourierBuilder courierBuilder;

    @Mock
    private CourierContractMapper courierContractMapper;

    private GetCourierCommandHandler getCourierCommandHandler;

    @BeforeAll
    public static void beforeAll() {
        courierApplicationFaker = new CourierApplicationFaker();
        courierFaker = new CourierFaker();
    }

    @BeforeEach
    public void beforeEach() {
        getCourierCommandHandler = new GetCourierCommandHandler(courierBuilder, courierContractMapper);
    }

    @Test
    public void should_handle() {
        //given
        final GetCourierCommand getCourierCommand = courierApplicationFaker.getCourierCommand();
        final UUID id = getCourierCommand.getId();
        final Courier courier = courierFaker.courier();
        final CourierContract courierContract = courierApplicationFaker.courierContract();

        //when
        when(courierBuilder.buildOptional(id)).thenReturn(Optional.of(courier));
        when(courierContractMapper.map(courier)).thenReturn(courierContract);

        final GetCourierCommandResult commandResult = getCourierCommandHandler.handle(getCourierCommand);

        //then
        assertThat(commandResult).isNotNull();
        assertThat(commandResult.getCourier()).isEqualTo(courierContract);
    }

    @Test
    public void should_throw_courier_not_found() {
        //given
        final GetCourierCommand getCourierCommand = courierApplicationFaker.getCourierCommand();
        final UUID id = getCourierCommand.getId();

        //when
        when(courierBuilder.buildOptional(id)).thenReturn(Optional.empty());

        final Throwable throwable = catchThrowable(() -> getCourierCommandHandler.handle(getCourierCommand));

        //then
        assertThat(throwable).isNotNull();
        assertThat(throwable).isInstanceOf(ApplicationException.class);
        final ApplicationException exception = (ApplicationException) throwable;
        assertThat(exception).isEqualTo(COURIER_NOT_FOUND);
    }
}

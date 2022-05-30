package com.migros.couriertracking.application.usecase.getcourier.command.handler;

import an.awesome.pipelinr.Command;
import com.migros.couriertracking.application.contract.mapper.CourierContractMapper;
import com.migros.couriertracking.application.usecase.getcourier.command.GetCourierCommand;
import com.migros.couriertracking.application.usecase.getcourier.command.result.GetCourierCommandResult;
import com.migros.couriertracking.domain.model.aggregate.Courier;
import com.migros.couriertracking.domain.model.aggregate.builder.CourierBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.migros.couriertracking.application.usecase.getcourier.command.handler.error.GetCourierCommandHandlerError.COURIER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class GetCourierCommandHandler implements Command.Handler<GetCourierCommand, GetCourierCommandResult> {

    private final CourierBuilder courierBuilder;

    private final CourierContractMapper courierContractMapper;

    @Override
    public GetCourierCommandResult handle(final GetCourierCommand getCourierCommand) {

        final Courier courier = courierBuilder.buildOptional(getCourierCommand.getId()).orElseThrow(() -> COURIER_NOT_FOUND);

        return GetCourierCommandResult.builder()
                .courier(courierContractMapper.map(courier))
                .build();
    }
}

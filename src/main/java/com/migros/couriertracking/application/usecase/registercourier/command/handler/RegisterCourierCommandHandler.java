package com.migros.couriertracking.application.usecase.registercourier.command.handler;

import an.awesome.pipelinr.Command;
import com.migros.couriertracking.application.usecase.registercourier.command.RegisterCourierCommand;
import com.migros.couriertracking.domain.model.aggregate.Courier;
import com.migros.couriertracking.domain.model.aggregate.dto.CreateCourierDto;
import com.migros.couriertracking.domain.model.aggregate.ofwork.CourierOfWork;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegisterCourierCommandHandler implements Command.Handler<RegisterCourierCommand, UUID> {

    private final CourierOfWork courierOfWork;

    @Override
    public UUID handle(final RegisterCourierCommand registerCourierCommand) {
        final Courier courier = Courier.create(CreateCourierDto.builder()
                .firstname(registerCourierCommand.getFirstname())
                .lastname(registerCourierCommand.getLastname())
                .build());
        courierOfWork.insert(courier);
        return courier.getId();
    }
}

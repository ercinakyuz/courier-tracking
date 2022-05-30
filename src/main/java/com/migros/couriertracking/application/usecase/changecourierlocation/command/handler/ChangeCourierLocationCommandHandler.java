package com.migros.couriertracking.application.usecase.changecourierlocation.command.handler;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Voidy;
import com.migros.couriertracking.application.usecase.changecourierlocation.command.ChangeCourierLocationCommand;
import com.migros.couriertracking.domain.model.aggregate.Courier;
import com.migros.couriertracking.domain.model.aggregate.builder.CourierBuilder;
import com.migros.couriertracking.domain.model.aggregate.ofwork.CourierOfWork;
import com.migros.couriertracking.domain.model.aggregate.value.Location;
import com.migros.couriertracking.domain.model.aggregate.value.dto.LoadLocationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChangeCourierLocationCommandHandler implements Command.Handler<ChangeCourierLocationCommand, Voidy> {

    private final CourierBuilder courierBuilder;

    private final CourierOfWork courierOfWork;

    @Override
    public Voidy handle(final ChangeCourierLocationCommand changeCourierLocationCommand) {
        final Courier courier = courierBuilder.build(changeCourierLocationCommand.getId());
        courier.changeLocation(Location.create(LoadLocationDto.builder()
                .latitude(changeCourierLocationCommand.getLatitude())
                .longitude(changeCourierLocationCommand.getLongitude())
                .build()));
        courierOfWork.update(courier);
        return null;
    }
}

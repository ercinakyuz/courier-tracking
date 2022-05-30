package com.migros.couriertracking.application.usecase.changecourierlocation.command;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Voidy;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ChangeCourierLocationCommand implements Command<Voidy> {

    private UUID id;

    private double latitude;

    private double longitude;
}


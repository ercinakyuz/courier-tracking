package com.migros.couriertracking.application.usecase.getcourier.command;

import an.awesome.pipelinr.Command;
import com.migros.couriertracking.application.usecase.getcourier.command.result.GetCourierCommandResult;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class GetCourierCommand implements Command<GetCourierCommandResult> {
    private UUID id;
}


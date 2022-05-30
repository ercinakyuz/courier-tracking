package com.migros.couriertracking.application.usecase.registercourier.command;

import an.awesome.pipelinr.Command;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class RegisterCourierCommand implements Command<UUID> {
    private String firstname;
    private String lastname;
}


package com.migros.couriertracking.api.controller;

import an.awesome.pipelinr.Pipeline;
import com.migros.couriertracking.api.model.request.ChangeCourierLocationRequest;
import com.migros.couriertracking.api.model.request.RegisterCourierRequest;
import com.migros.couriertracking.api.model.response.GetCourierResponse;
import com.migros.couriertracking.api.model.response.RegisterCourierResponse;
import com.migros.couriertracking.application.usecase.getcourier.command.GetCourierCommand;
import com.migros.couriertracking.application.usecase.getcourier.command.result.GetCourierCommandResult;
import com.migros.couriertracking.application.usecase.changecourierlocation.command.ChangeCourierLocationCommand;
import com.migros.couriertracking.application.usecase.registercourier.command.RegisterCourierCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static com.migros.couriertracking.api.model.url.ApiUrlPath.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(COURIER_BASE_URL)
@RequiredArgsConstructor
public class CourierController {
    private final Pipeline pipeline;

    @PostMapping(RELATIVE_COURIER_REGISTRATION_URL)
    public ResponseEntity<RegisterCourierResponse> register(
            @Valid @RequestBody final RegisterCourierRequest request) {
        final UUID id = pipeline.send(RegisterCourierCommand.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .build());
        return new ResponseEntity<>(RegisterCourierResponse.builder()
                .result(id)
                .build(), CREATED);
    }

    @PatchMapping(RELATIVE_COURIER_CHANGE_LOCATION_URL)
    public ResponseEntity<Void> changeLocation(
            @PathVariable final UUID id,
            @Valid @RequestBody final ChangeCourierLocationRequest request) {
        pipeline.send(ChangeCourierLocationCommand.builder()
                .id(id)
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .build());
        return ResponseEntity.ok().build();
    }

    @GetMapping(RELATIVE_COURIER_BY_ID_URL)
    public ResponseEntity<GetCourierResponse> get(
            @PathVariable final UUID id) {
        final GetCourierCommandResult getCourierCommandResult = pipeline.send(GetCourierCommand.builder()
                .id(id)
                .build());
        return new ResponseEntity<>(GetCourierResponse.builder()
                .result(getCourierCommandResult.getCourier())
                .build(), OK);
    }
}

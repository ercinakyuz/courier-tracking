package com.migros.couriertracking.application.usecase.getcourier.command.result;

import com.migros.couriertracking.application.contract.CourierContract;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetCourierCommandResult {

    private CourierContract courier;
}

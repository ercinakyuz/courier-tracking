package com.migros.couriertracking.api.model.response;

import com.migros.couriertracking.application.contract.CourierContract;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class GetCourierResponse extends AbstractResponse<CourierContract>{
}

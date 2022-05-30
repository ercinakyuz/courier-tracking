package com.migros.couriertracking.api.error.response;


import com.migros.couriertracking.api.error.contract.ErrorContract;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private ErrorContract error;
}

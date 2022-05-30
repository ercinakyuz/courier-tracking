package com.migros.couriertracking.api.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class AbstractResponse<TResult> {
    private TResult result;
}

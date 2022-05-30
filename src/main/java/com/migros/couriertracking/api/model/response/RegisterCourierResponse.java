package com.migros.couriertracking.api.model.response;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
public class RegisterCourierResponse extends AbstractResponse<UUID>{
}

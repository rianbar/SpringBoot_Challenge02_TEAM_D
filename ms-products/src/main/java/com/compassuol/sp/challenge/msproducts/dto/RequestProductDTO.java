package com.compassuol.sp.challenge.msproducts.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;


@Data
@Validated
@AllArgsConstructor
public class RequestProductDTO {

    @NotEmpty(message = "field 'name' is mandatory")
    String name;
    @NotNull(message = "field 'description' cannot be null")
    @Size(min = 10, message = "field 'description' must have at least 10 characters")
    String description;
    @NotNull(message = "field 'value' cannot be null")
    @Min(value = 0L, message = "field 'value' cannot negative")
    Double value;
}

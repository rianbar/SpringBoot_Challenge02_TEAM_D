package com.compassuol.sp.challenge.msproducts.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ResponseProductDTO {
    private Long id;
    private String name;
    private String description;
    private Double value;
}

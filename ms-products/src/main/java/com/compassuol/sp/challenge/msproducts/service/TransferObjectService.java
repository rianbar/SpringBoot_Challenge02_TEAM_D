package com.compassuol.sp.challenge.msproducts.service;

import com.compassuol.sp.challenge.msproducts.dto.RequestProductDTO;
import com.compassuol.sp.challenge.msproducts.dto.ResponseProductDTO;
import com.compassuol.sp.challenge.msproducts.model.ProductModel;
import org.springframework.stereotype.Service;

@Service
public class TransferObjectService {

    public ProductModel toModelUpdated(long id, RequestProductDTO dto) {
        return ProductModel.builder()
                .id(id)
                .name(dto.getName())
                .description(dto.getDescription())
                .value(dto.getValue())
                .build();
    }

    public ProductModel toModel(RequestProductDTO dto) {
        return ProductModel.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .value(dto.getValue())
                .build();
    }

    public ResponseProductDTO toDTO(ProductModel model) {
        return ResponseProductDTO.builder()
                .id(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .value(model.getValue())
                .build();
    }
}

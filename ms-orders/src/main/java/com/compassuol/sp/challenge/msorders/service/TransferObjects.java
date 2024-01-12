package com.compassuol.sp.challenge.msorders.service;

import com.compassuol.sp.challenge.msorders.constant.StatusOrderEnum;
import com.compassuol.sp.challenge.msorders.dto.RequestOrderDTO;
import com.compassuol.sp.challenge.msorders.dto.ViaCepAddressDTO;
import com.compassuol.sp.challenge.msorders.model.AddressModel;
import com.compassuol.sp.challenge.msorders.model.OrderModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@Component
@RequiredArgsConstructor
public class TransferObjects {

    public AddressModel fillAddressModel(RequestOrderDTO request, ViaCepAddressDTO cep) {
        return AddressModel.builder()
                .number(request.getAddress().getNumber())
                .complement(cep.getComplemento())
                .city(cep.getLocalidade())
                .state(cep.getUf())
                .postalCode(cep.getCep())
                .street(request.getAddress().getStreet())
                .build();
    }

    public OrderModel fillOrderObject(RequestOrderDTO request, AddressModel address, double subtotal)
            throws ParseException {
        return new OrderModel(request.getProducts(), address, request.getPaymentMethod(),
                subtotal, StatusOrderEnum.CONFIRMED, "");
    }

    public OrderModel updateOrderObject(OrderModel order, RequestOrderDTO request, ViaCepAddressDTO cep) {
        order.setPaymentMethod(request.getPaymentMethod());
        AddressModel address = fillAddressModel(request, cep);
        order.setAddress(address);
        order.setProducts(request.getProducts());
        return order;
    }
}

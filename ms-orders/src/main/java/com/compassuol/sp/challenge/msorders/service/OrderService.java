package com.compassuol.sp.challenge.msorders.service;

import com.compassuol.sp.challenge.msorders.constant.StatusOrderEnum;
import com.compassuol.sp.challenge.msorders.dto.*;
import com.compassuol.sp.challenge.msorders.errors.BusinessErrorException;
import com.compassuol.sp.challenge.msorders.errors.OrderCancelNotAllowedException;
import com.compassuol.sp.challenge.msorders.errors.OrderNotFoundException;
import com.compassuol.sp.challenge.msorders.model.AddressModel;
import com.compassuol.sp.challenge.msorders.model.OrderModel;
import com.compassuol.sp.challenge.msorders.model.OrderProductsModel;
import com.compassuol.sp.challenge.msorders.proxy.ProductsProxy;
import com.compassuol.sp.challenge.msorders.proxy.ViaCepProxy;
import com.compassuol.sp.challenge.msorders.repository.OrderRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductsProxy proxy;
    private final ViaCepProxy viaCepProxy;
    private final TransferObjects transferObjects;

    public List<OrderModel> getOrdersByStatusSortedByDate(StatusOrderEnum status) {
        if (status == null) {
            return orderRepository.findOrdersByCreateDateDesc();
        }
        return orderRepository.findOrdersByStatusAndCreateDateDesc(status);
    }

    public Optional<OrderModel> findByIdService(Long id) {
        var order = orderRepository.findById(id);
        if (order.isEmpty()) throw new OrderNotFoundException("order doesn't exists");
        return order;
    }

    public CreateOrderResponseDTO createOrderService(RequestOrderDTO request) {
        double subtotalValue = 0.0;
        for (OrderProductsModel productsModel : request.getProducts()) {
            try {
                ProductModelDTO product = proxy.getProductById(productsModel.getProductId());
                subtotalValue += productsModel.getQuantity() * product.getValue();
            } catch (FeignException ex) {
                throw new BusinessErrorException("cannot find product id or your connection with" +
                        " products microservice is falling");
            }
        }
        try {
            ViaCepAddressDTO cep = viaCepProxy.getViaCepAddress(request.getAddress().getPostalCode());
            AddressModel address = transferObjects.fillAddressModel(request, cep);
            OrderModel order = transferObjects.fillOrderObject(request, address, subtotalValue);
            return new CreateOrderResponseDTO(orderRepository.save(order));
        } catch (ParseException ex) {
            throw new RuntimeException();
        }
    }

    public OrderModel cancelOrderByIdService(Long id, CancelOrderRequestDTO cancelOrderRequest) {

        var order = orderRepository.findById(id);
        long daysBetween;
        if (order.isPresent()) {
            if (order.get().getStatus() == StatusOrderEnum.SENT) {
                throw new OrderCancelNotAllowedException("order cannot be canceled, because it has already been sent");}

            daysBetween = Duration.between(order.get().getCreateDate(), LocalDateTime.now()).toDays();
            if (daysBetween > 90) throw new
                    OrderCancelNotAllowedException("order cannot be canceled because the time has been exceeded 90 days");

            order.get().setStatus(StatusOrderEnum.CANCELED);
            order.get().setCancelDate(LocalDateTime.now());
            order.get().setCancelReason(cancelOrderRequest.getCancelReason());
        }

        return orderRepository.save(order.get());
    }

    public OrderModel updateOrderService(Long id, RequestOrderDTO request) {
        OrderModel order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("order not found"));

        if (order.getStatus() == StatusOrderEnum.CANCELED || order.getStatus() == StatusOrderEnum.SENT)
            throw new BusinessErrorException("order with status 'canceled' or 'sent' cannot be updated.");

        ViaCepAddressDTO cep = viaCepProxy.getViaCepAddress(request.getAddress().getPostalCode());
        OrderModel updateOrder = transferObjects.updateOrderObject(order,request,cep);
        updateOrder.setStatus(StatusOrderEnum.SENT);
        return orderRepository.save(updateOrder);
    }
}
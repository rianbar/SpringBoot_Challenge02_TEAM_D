package com.compassuol.sp.challenge.msorders.service;

import com.compassuol.sp.challenge.msorders.constant.StatusOrderEnum;
import com.compassuol.sp.challenge.msorders.dto.CancelOrderRequestDTO;
import com.compassuol.sp.challenge.msorders.errors.BusinessErrorException;
import com.compassuol.sp.challenge.msorders.errors.OrderCancelNotAllowedException;
import com.compassuol.sp.challenge.msorders.errors.OrderNotFoundException;
import com.compassuol.sp.challenge.msorders.model.OrderModel;
import com.compassuol.sp.challenge.msorders.proxy.ProductsProxy;
import com.compassuol.sp.challenge.msorders.proxy.ViaCepProxy;
import com.compassuol.sp.challenge.msorders.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.compassuol.sp.challenge.msorders.constants.ConstantOrders.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductsProxy proxy;

    @Mock
    private ViaCepProxy viaCepProxy;

    @Mock
    private TransferObjects transferObjects;

    @Test
    void findOrderById_withInvalidData_returnException() {
        when(orderRepository.findById(anyLong())).thenThrow(OrderNotFoundException.class);

        assertThatThrownBy(() -> orderService.findByIdService(1L))
                .isInstanceOf(OrderNotFoundException.class);
    }

    @Test
    void findOrderById_withValidData_returnsObject() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(ORDER_RESPONSE));

        var response = orderService.findByIdService(1L);

        assertNotNull(response);
    }

    @Test
    void createOrder_withValidData_returnsObject() throws ParseException {
        var orderModelCaptor = ArgumentCaptor.forClass(OrderModel.class);
        when(proxy.getProductById(anyLong())).thenReturn(PRODUCT_MODEL_DTO);
        when(viaCepProxy.getViaCepAddress(anyString())).thenReturn(VIA_CEP_ADDRESS_DTO);
        when(transferObjects.fillAddressModel(REQUEST_ORDER_DTO, VIA_CEP_ADDRESS_DTO)).thenReturn(ADDRESS_MODEL);
        when(transferObjects.fillOrderObject(any(), any(), anyDouble())).thenReturn(ORDER_RESPONSE);
        when(orderRepository.save(orderModelCaptor.capture())).thenReturn(ORDER_RESPONSE);

        var response = orderService.createOrderService(REQUEST_ORDER_DTO);

        assertNotNull(response);
    }

    @Test
    void cancelOrder_withInvalidStatus_returnException() {
        var cancelReason = new CancelOrderRequestDTO();
        var order = ORDER_RESPONSE;
        order.setStatus(StatusOrderEnum.SENT);
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));


        assertThatThrownBy(() -> orderService.cancelOrderByIdService(1L, cancelReason))
                .isInstanceOf(OrderCancelNotAllowedException.class);

    }

    @Test
    void cancelOrder_withInvalidDateTime_returnException() {
        var cancelReason = new CancelOrderRequestDTO();
        var exceededDate = LocalDateTime.of(2020, 12, 17, 19, 50, 15);
        var order = ORDER_RESPONSE;
        order.setCreateDate(exceededDate);

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        assertThatThrownBy(() -> orderService.cancelOrderByIdService(1L, cancelReason))
                .isInstanceOf(OrderCancelNotAllowedException.class);
    }

    @Test
    void cancelOrder_withValidData_returnObject() {
        var cancelReason = new CancelOrderRequestDTO();
        cancelReason.setCancelReason("cancel reason");
        var order = ORDER_RESPONSE;
        order.setCreateDate(LocalDateTime.now());
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);

        var response = orderService.cancelOrderByIdService(1L, cancelReason);

        assertNotNull(response);
    }

    @Test
    void updateOrder_withInvalidId_returnException() {
        when(orderRepository.findById(anyLong())).thenThrow(OrderNotFoundException.class);

        assertThatThrownBy(() -> orderService.updateOrderService(1L, REQUEST_ORDER_DTO))
                .isInstanceOf(OrderNotFoundException.class);
    }

    @Test
    void updateOrder_withInvalidStatus_returnException() {
        var order = ORDER_RESPONSE;
        order.setStatus(StatusOrderEnum.CANCELED);

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        assertThatThrownBy(() -> orderService.updateOrderService(1L, REQUEST_ORDER_DTO))
                .isInstanceOf(BusinessErrorException.class);
    }

    @Test
    void updateOrder_withValidData_returnObject() {
        var orderModelCaptor = ArgumentCaptor.forClass(OrderModel.class);
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(ORDER_RESPONSE));
        when(viaCepProxy.getViaCepAddress(anyString())).thenReturn(VIA_CEP_ADDRESS_DTO);
        when(transferObjects.updateOrderObject(any(), any(), any())).thenReturn(ORDER_RESPONSE);
        when(orderRepository.save(orderModelCaptor.capture())).thenReturn(ORDER_RESPONSE);

        var response = orderService.updateOrderService(1L, REQUEST_ORDER_DTO);

        assertNotNull(response);
    }
}

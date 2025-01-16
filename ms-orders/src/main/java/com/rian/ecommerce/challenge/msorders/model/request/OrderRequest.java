package com.rian.ecommerce.challenge.msorders.model.request;

import java.util.List;

import com.rian.ecommerce.challenge.msorders.constant.PaymentType;
import com.rian.ecommerce.challenge.msorders.model.OrderProducts;

public record OrderRequest(
  List<OrderProducts> products,
  AddressRequest address,
  PaymentType paymentType
  ){
}

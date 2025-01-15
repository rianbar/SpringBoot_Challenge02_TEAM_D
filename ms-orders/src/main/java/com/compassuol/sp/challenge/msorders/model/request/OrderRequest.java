package com.compassuol.sp.challenge.msorders.model.request;

import java.util.List;

import com.compassuol.sp.challenge.msorders.constant.PaymentType;
import com.compassuol.sp.challenge.msorders.model.OrderProducts;

public record OrderRequest(
  List<OrderProducts> products,
  AddressRequest address,
  PaymentType paymentType
  ){
}

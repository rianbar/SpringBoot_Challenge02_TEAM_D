package com.rian.ecommerce.challenge.msorders.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.rian.ecommerce.challenge.msorders.model.response.AddressResponse;

@FeignClient(name = "ms-address-provider", url = "${provider.address.url}")
public interface AddressProviderClient {
  @GetMapping("/{zipCode}/json/")
  AddressResponse getAdderss(@PathVariable String zipCode);
}

package com.nix.phonemarketgatewayservice.client;


import com.nix.phonemarketgatewayservice.dto.OrderDto;
import com.nix.phonemarketgatewayservice.dto.PhoneDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient("order-catalog-service")
public interface OrderCatalogClient {

    @PostMapping("api/orders")
    OrderDto createOrder(@RequestBody OrderDto createOrderDto);

    @GetMapping("api/orders/{uuid}")
    OrderDto findByUuid(@PathVariable("uuid") String uuid);

    @PutMapping("api/orders/{uuid}/phones")
    OrderDto addPhoneToOrder(@PathVariable("uuid") String uuid, @RequestBody PhoneDto addPhoneOrderDto);

    @DeleteMapping("api/orders/{uuid}/phones/{phoneId}")
    OrderDto deletePhoneFromOrder(@PathVariable("uuid") String uuid, @PathVariable("phoneId") Long phoneId);


}

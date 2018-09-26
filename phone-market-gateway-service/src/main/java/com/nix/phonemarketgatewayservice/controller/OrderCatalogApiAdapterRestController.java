package com.nix.phonemarketgatewayservice.controller;

import com.nix.phonemarketgatewayservice.dto.CalculateOrderDto;
import com.nix.phonemarketgatewayservice.dto.CreateOrderDto;
import com.nix.phonemarketgatewayservice.dto.OrderDto;
import com.nix.phonemarketgatewayservice.dto.PhoneDto;
import com.nix.phonemarketgatewayservice.service.OrderService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
public class OrderCatalogApiAdapterRestController {

    private final OrderService orderService;

    @ApiOperation(value = "Create order")
    @PostMapping("api/orders")
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody CreateOrderDto createOrderDto) {
        return ResponseEntity.ok(orderService.createOrder(createOrderDto));
    }

    @ApiOperation(value = "Calculate order price")
    @PutMapping("api/orders/{uuid}/calculate")
    public ResponseEntity<CalculateOrderDto> calculateOrder(@PathVariable String uuid) {
        return ResponseEntity.ok(orderService.calculateOrder(uuid));
    }

    @ApiOperation(value = "Add phone to order")
    @PutMapping("api/orders/{uuid}/phones")
    public ResponseEntity<OrderDto> addPhoneToOrder(@PathVariable String uuid, @Valid @RequestBody PhoneDto addPhoneOrderDto) {
        return ResponseEntity.ok(orderService.addPhoneToOrder(uuid, addPhoneOrderDto));
    }

    @ApiOperation(value = "Delete phone from order")
    @DeleteMapping("api/orders/{uuid}/phones/{phoneId}")
    public ResponseEntity<OrderDto> deletePhoneFromOrder(@PathVariable String uuid, @PathVariable Long phoneId) {
        return ResponseEntity.ok(orderService.deletePhoneFromOrder(uuid, phoneId));
    }

}

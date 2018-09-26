package com.nix.orderservice.controller;

import com.nix.orderservice.dto.OrderDto;
import com.nix.orderservice.dto.PhoneDto;
import com.nix.orderservice.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("api/orders/{uuid}")
    public ResponseEntity<OrderDto> getOrderByUuid(@PathVariable String uuid) {
        return orderService.findByUuid(uuid)
                .map(orderEntity -> ResponseEntity.ok(orderEntity.toDto()))
                .orElseThrow(() -> new RuntimeException("Can't find order by id"));
    }

    @PostMapping("api/orders")
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody OrderDto createOrderDto) {
        return ResponseEntity.ok(orderService.createOrder(createOrderDto).toDto());
    }

    @PutMapping("api/orders/{uuid}/phones")
    public ResponseEntity<OrderDto> addPhoneToOrder(@PathVariable String uuid, @Valid @RequestBody PhoneDto addPhoneOrderDto) {
        return ResponseEntity.ok(orderService.addPhoneToOrder(uuid, addPhoneOrderDto).toDto());
    }

    @DeleteMapping("api/orders/{uuid}/phones/{phoneId}")
    public void deletePhoneFromOrder(@PathVariable String uuid, @PathVariable Long phoneId) {
        orderService.deletePhoneFromOrder(uuid, phoneId);
    }
}

package com.nix.orderservice.service;

import com.nix.orderservice.domain.OrderEntity;
import com.nix.orderservice.domain.PhoneOrderEntity;
import com.nix.orderservice.dto.OrderDto;
import com.nix.orderservice.dto.PhoneDto;
import com.nix.orderservice.repository.OrderRepository;
import com.nix.orderservice.repository.PhoneOrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final PhoneOrderRepository phoneOrderRepository;

    @Transactional
    public OrderEntity createOrder(OrderDto createOrder) {
        OrderEntity order = new OrderEntity();
        order.setFirstName(createOrder.getFirstName());
        order.setLastName(createOrder.getLastName());
        order.setEmail(createOrder.getEmail());

        List<PhoneOrderEntity> phones = new ArrayList<>();

        createOrder.getPhones().forEach(phoneOrderDto -> {
            PhoneOrderEntity phoneOrder = new PhoneOrderEntity();
            phoneOrder.setOrder(order);
            phoneOrder.setUuid(phoneOrderDto.getUuid());
            phoneOrder.setName(phoneOrderDto.getName());
            phoneOrder.setDescription(phoneOrderDto.getDescription());
            phoneOrder.setImageUrl(phoneOrderDto.getImageUrl());
            phoneOrder.setPrice(phoneOrderDto.getPrice());
            phones.add(phoneOrder);
        });

        order.setPhones(phones);

        orderRepository.save(order);

        return order;
    }

    public OrderEntity deletePhoneFromOrder(String uuid, Long phoneId) {
        PhoneOrderEntity phoneOrder = phoneOrderRepository.findById(phoneId).orElseThrow(
                () -> new RuntimeException("Can't find phone"));

        if (!phoneOrder.getOrder().getUuid().equals(uuid)) {
            throw new RuntimeException("Invalid order or phone uuid");
        }

        phoneOrderRepository.delete(phoneOrder);

        return orderRepository.findByUuid(uuid).get();
    }

    public Optional<OrderEntity> findByUuid(String uuid) {
        return orderRepository.findByUuid(uuid);
    }

    @Transactional
    public OrderEntity addPhoneToOrder(String uuid, PhoneDto addPhoneOrderDto) {
        OrderEntity order = orderRepository.findByUuid(uuid).orElseThrow(
                () -> new RuntimeException("Can't find order"));

        PhoneOrderEntity phoneOrder = new PhoneOrderEntity();
        phoneOrder.setOrder(order);
        phoneOrder.setUuid(addPhoneOrderDto.getUuid());
        phoneOrder.setName(addPhoneOrderDto.getName());
        phoneOrder.setDescription(addPhoneOrderDto.getDescription());
        phoneOrder.setImageUrl(addPhoneOrderDto.getImageUrl());
        phoneOrder.setPrice(addPhoneOrderDto.getPrice());

        order.getPhones().add(phoneOrder);

        orderRepository.save(order);

        return order;
    }
}

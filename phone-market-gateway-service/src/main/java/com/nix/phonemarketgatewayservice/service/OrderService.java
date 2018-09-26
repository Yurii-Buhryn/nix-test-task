package com.nix.phonemarketgatewayservice.service;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.nix.phonemarketgatewayservice.client.OrderCatalogClient;
import com.nix.phonemarketgatewayservice.client.PhoneCatalogClient;
import com.nix.phonemarketgatewayservice.constant.PhoneStatus;
import com.nix.phonemarketgatewayservice.dto.CalculateOrderDto;
import com.nix.phonemarketgatewayservice.dto.CreateOrderDto;
import com.nix.phonemarketgatewayservice.dto.OrderDto;
import com.nix.phonemarketgatewayservice.dto.PhoneDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderCatalogClient orderCatalogClient;

    private final PhoneCatalogClient phoneCatalogClient;

    public OrderDto createOrder(CreateOrderDto createOrderDto) {
        OrderDto orderDto = new OrderDto();
        orderDto.setEmail(createOrderDto.getEmail());
        orderDto.setFirstName(createOrderDto.getFirstName());
        orderDto.setLastName(createOrderDto.getLastName());

        createOrderDto.getPhoneUuids().forEach(phoneUuid -> {
            PhoneDto phoneData = phoneCatalogClient.readPhoneByUuid(phoneUuid);
            orderDto.getPhones().add(phoneData);
        });

        return orderCatalogClient.createOrder(orderDto);
    }

    public CalculateOrderDto calculateOrder(String uuid) {
        OrderDto order = orderCatalogClient.findByUuid(uuid);

        CalculateOrderDto calculateOrderDto = new CalculateOrderDto();
        calculateOrderDto.setUuid(order.getUuid());
        calculateOrderDto.setEmail(order.getEmail());
        calculateOrderDto.setFirstName(order.getFirstName());
        calculateOrderDto.setLastName(order.getLastName());

        order.getPhones().forEach(phone -> {
            PhoneDto phoneData = phone;

            // TODO: It will be better to configure client return something like optional but this just for demo case

            try {
                phoneData = phoneCatalogClient.readPhoneByUuid(phone.getUuid());
                phoneData.setStatus(PhoneStatus.AVAILABLE);
            } catch (Exception e) {
                phoneData.setStatus(PhoneStatus.UNAVAILABLE);
                phoneData.setPrice(0D);
            }

            calculateOrderDto.getPhones().add(phoneData);

        });

        calculateOrderDto.setPrice(
                calculateOrderDto.getPhones().stream().mapToDouble(PhoneDto::getPrice).sum()
        );

        return calculateOrderDto;
    }


    public OrderDto addPhoneToOrder(String uuid, PhoneDto addPhoneOrderDto) {
        return orderCatalogClient.addPhoneToOrder(uuid, addPhoneOrderDto);
    }

    public OrderDto deletePhoneFromOrder(String uuid, Long phoneId) {
        return orderCatalogClient.deletePhoneFromOrder(uuid, phoneId);
    }

}

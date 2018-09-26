package com.nix.phonemarketgatewayservice;

import com.nix.phonemarketgatewayservice.client.OrderCatalogClient;
import com.nix.phonemarketgatewayservice.client.PhoneCatalogClient;
import com.nix.phonemarketgatewayservice.constant.PhoneStatus;
import com.nix.phonemarketgatewayservice.dto.CalculateOrderDto;
import com.nix.phonemarketgatewayservice.dto.CreateOrderDto;
import com.nix.phonemarketgatewayservice.dto.OrderDto;
import com.nix.phonemarketgatewayservice.dto.PhoneDto;
import com.nix.phonemarketgatewayservice.service.OrderService;
import lombok.NoArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
@NoArgsConstructor
public class OrderServiceTest {

    // Test for controller should be added for real app the same as Phone Catalog service

    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderCatalogClient orderCatalogClient;

    @MockBean
    private PhoneCatalogClient phoneCatalogClient;

    @Test
    public void createOrder() {
        PhoneDto addedPhoneResponse = PhoneDto.builder()
                .name("test")
                .description("test descrioption")
                .price(1D)
                .uuid("test-test-test-test")
                .imageUrl("test")
                .status(PhoneStatus.AVAILABLE)
                .build();

        OrderDto createOrderResponse = OrderDto.builder()
                .uuid("test-order-created-from-test")
                .email("test@gmail.com")
                .firstName("Test")
                .lastName("Clienmt")
                .phones(Collections.singletonList(addedPhoneResponse))
                .build();

        given(phoneCatalogClient.readPhoneByUuid(any())).willReturn(addedPhoneResponse);
        given(orderCatalogClient.createOrder(any())).willReturn(createOrderResponse);

        CreateOrderDto createOrderDto = CreateOrderDto.builder()
                .email("test@gmail.com")
                .firstName("Test")
                .lastName("Clienmt")
                .phoneUuids(Collections.singletonList("test-test-test-test"))
                .build();


        OrderDto createdOrder = orderService.createOrder(createOrderDto);
        assertEquals(createdOrder.getEmail(), "test@gmail.com");
        assertEquals(createdOrder.getUuid(), "test-order-created-from-test");
        assertEquals(createdOrder.getPhones().size(), 1);
    }

    @Test
    public void calculateOrderPrice() {
        PhoneDto orderPhoneResponse1 = PhoneDto.builder()
                .name("test")
                .description("test descrioption")
                .price(100D)
                .uuid("test-test-test-test-1")
                .imageUrl("test")
                .build();

        PhoneDto orderPhoneResponse2 = PhoneDto.builder()
                .name("test")
                .description("test descrioption")
                .price(300D)
                .uuid("test-test-test-test-2")
                .imageUrl("test")
                .build();

        PhoneDto orderPhoneResponse3 = PhoneDto.builder()
                .name("test")
                .description("test descrioption")
                .price(300D)
                .uuid("test-test-test-test-3")
                .imageUrl("test")
                .build();

        OrderDto createOrderResponse = OrderDto.builder()
                .uuid("test-order-created-from-test")
                .email("test@gmail.com")
                .firstName("Test")
                .lastName("Clienmt")
                .phones(Arrays.asList(orderPhoneResponse1, orderPhoneResponse2, orderPhoneResponse3))
                .build();

        given(phoneCatalogClient.readPhoneByUuid("test-test-test-test-1")).willReturn(orderPhoneResponse1);
        given(phoneCatalogClient.readPhoneByUuid("test-test-test-test-2")).willReturn(orderPhoneResponse2);
        given(phoneCatalogClient.readPhoneByUuid("test-test-test-test-3")).willThrow(new RuntimeException("Can't find phone"));

        given(orderCatalogClient.findByUuid(any())).willReturn(createOrderResponse);

        CalculateOrderDto calculatedOrder = orderService.calculateOrder("test-order-created-from-test");
        assertEquals(calculatedOrder.getEmail(), "test@gmail.com");
        assertEquals(calculatedOrder.getUuid(), "test-order-created-from-test");
        assertEquals(calculatedOrder.getPhones().size(), 3);
        assertEquals(
                calculatedOrder.getPhones().stream()
                        .filter(phone -> phone.getStatus().equals(PhoneStatus.AVAILABLE))
                        .count(), 2L);
        assertEquals(
                calculatedOrder.getPhones().stream()
                        .filter(phone -> phone.getStatus().equals(PhoneStatus.UNAVAILABLE))
                        .count(), 1L);
        assertEquals(calculatedOrder.getPrice(), new Double(400));
    }
}

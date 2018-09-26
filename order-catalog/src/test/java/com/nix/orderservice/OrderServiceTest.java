package com.nix.orderservice;

import com.nix.orderservice.domain.OrderEntity;
import com.nix.orderservice.dto.OrderDto;
import com.nix.orderservice.dto.PhoneDto;
import com.nix.orderservice.service.OrderService;
import lombok.NoArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@NoArgsConstructor
public class OrderServiceTest {

    // In case of real application test data should be created separately in test configs
    // not in global liquibase evolutions
    // Logic to clean all data after each test should be implemented to in case of real app
    // Controller test should be similar as we have in Phone Service

    @Autowired
    private OrderService orderService;

    @Test
    public void findOrderByUuid() {
        Optional<OrderEntity> order = orderService.findByUuid("29cf3b69-cd49-4517-bea3-7ca7e7f0f1de");
        assertTrue(order.isPresent());
        assertEquals(order.get().getEmail(), "customer1@gmail.com");
    }

    @Test
    public void findOrderByInvalidUuid() {
        Optional<OrderEntity> order = orderService.findByUuid("invalid-uuid");
        assertFalse(order.isPresent());
    }


    @Test
    public void addPhoneToOrder() {
        PhoneDto phoneToAdd = PhoneDto.builder()
                .uuid("new-added-phone")
                .name("Iphone XS")
                .description("The last version of Iphone")
                .price(1000D)
                .imageUrl("https://someserver/images/asghuiuashgia-aushgiuahsgiu-aisughiausgh")
                .build();

        OrderEntity updatedOrder = orderService.addPhoneToOrder("29cf3b69-cd49-4517-bea3-7ca7e7f0f1de", phoneToAdd);

        assertEquals(updatedOrder.getPhones().size(), 3);
    }


    @Test
    public void addPhoneToNotExistingOrder() {
        PhoneDto phoneToAdd = PhoneDto.builder()
                .uuid("3716a81b-4092-478c-a2d1-e8f847c864a4")
                .name("Iphone XS")
                .description("The last version of Iphone")
                .price(1000D)
                .imageUrl("https://someserver/images/asghuiuashgia-aushgiuahsgiu-aisughiausgh")
                .build();

        assertThatThrownBy(() -> orderService.addPhoneToOrder("not-existing-order", phoneToAdd))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Can't find order");
    }

    @Test
    public void createOrder() {
        OrderDto createDto = OrderDto.builder()
                .email("test@gmail.com")
                .firstName("Test")
                .lastName("Clienmt")
                .phones(Collections.singletonList(
                        PhoneDto.builder()
                                .name("test")
                                .description("test descrioption")
                                .price(1D)
                                .uuid("test-test-test-test")
                                .imageUrl("test")
                                .build()
                        )
                )
                .build();

        OrderEntity createdOrder = orderService.createOrder(createDto);

        assertNotNull(createdOrder.getId());
        assertNotNull(createdOrder.getUuid());
        assertEquals(createdOrder.getEmail(), "test@gmail.com");
        assertEquals(createdOrder.getPhones().size(), 1);
    }

    @Test
    public void deletePhoneFromOrder() {
        OrderEntity updatedOrder = orderService.deletePhoneFromOrder("03ef381d-5aa0-44ff-8a66-26c7c6c9277f", 6L);

        assertEquals(updatedOrder.getPhones().size(), 0);
    }

    @Test
    public void deletePhoneFromNotExistingOrder() {
        assertThatThrownBy(() -> orderService.deletePhoneFromOrder("not-existing-order", 6L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Invalid order or phone uuid");
    }

    @Test
    public void deleteNotExistingPhoneFromOrder() {
        assertThatThrownBy(() -> orderService.deletePhoneFromOrder("03ef381d-5aa0-44ff-8a66-26c7c6c9277f", 123L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Can't find phone");
    }
}

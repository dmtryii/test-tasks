package com.dmtryii.internetshop.test_service;

import com.dmtryii.internetshop.model.Order;
import com.dmtryii.internetshop.model.User;
import com.dmtryii.internetshop.model.enums.EState;
import com.dmtryii.internetshop.repository.OrderLineRepository;
import com.dmtryii.internetshop.repository.OrderRepository;
import com.dmtryii.internetshop.service.ProductService;
import com.dmtryii.internetshop.service.UserService;
import com.dmtryii.internetshop.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.Principal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.when;

@SpringBootTest
public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderLineRepository orderLineRepository;
    @Mock
    private ProductService productService;
    @Mock
    private UserService userService;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Value("${remove_unpaid_orders.min}")
    private long deletionDelay;

    @Test
    public void CreateOrder() {

        Principal principal = Mockito.mock(Principal.class);
        Mockito.when(principal.getName()).thenReturn("testUser");

        User mockUser = new User();
        mockUser.setUsername("testUser");
        when(userService.getUserByPrincipal(principal)).thenReturn(mockUser);

        Order expectedOrder = Order.builder()
                .dateOfOrder(LocalDateTime.now())
                .user(mockUser)
                .state(EState.WAITING_FOR_PAYMENT)
                .build();

        when(orderRepository.save(Mockito.any(Order.class))).thenReturn(expectedOrder);

        Order createdOrder = orderService.create(principal);

        Mockito.verify(orderRepository, Mockito.times(1)).save(Mockito.any(Order.class));

        assertEquals(expectedOrder, createdOrder);
    }
}

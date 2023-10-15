package com.dmtryii.internetshop.service.impl;

import com.dmtryii.internetshop.exception.ResourceNotFoundException;
import com.dmtryii.internetshop.model.Order;
import com.dmtryii.internetshop.model.OrderLine;
import com.dmtryii.internetshop.model.Product;
import com.dmtryii.internetshop.model.User;
import com.dmtryii.internetshop.model.enums.EState;
import com.dmtryii.internetshop.repository.OrderLineRepository;
import com.dmtryii.internetshop.repository.OrderRepository;
import com.dmtryii.internetshop.service.OrderService;
import com.dmtryii.internetshop.service.ProductService;
import com.dmtryii.internetshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    @Value("${remove_unpaid_orders.min}")
    private long deletionDelay;

    private final OrderRepository orderRepository;
    private final OrderLineRepository orderLineRepository;
    private final ProductService productService;
    private final UserService userService;

    @Override
    public Order create(Principal principal) {
        User user = userService.getUserByPrincipal(principal);

        Order order = Order.builder()
                .dateOfOrder(LocalDateTime.now())
                .user(user)
                .state(EState.WAITING_FOR_PAYMENT)
                .build();

        return orderRepository.save(order);
    }

    @Override
    public OrderLine addProduct(Long productId, Integer quantity, Principal principal) {

        User user = userService.getUserByPrincipal(principal);
        Order order = getOrderForUser(user);

        if (order == null) {
            order = create(principal);
        }

        Product product = productService.getById(productId);
        productService.changeQuantity(productId, -quantity);

        OrderLine orderLine = new OrderLine(order, product, quantity);
        return orderLineRepository.save(orderLine);
    }

    @Override
    public Order pay(Principal principal) {

        User user = userService.getUserByPrincipal(principal);
        Order order = orderRepository.findOrderByUser(user);

        if(order == null) {
            throw new ResourceNotFoundException(user.getUsername() + " has no orders");
        }
        order.setState(EState.SUCCESSFUL_PAYMENT);
        return orderRepository.save(order);
    }

    @Scheduled(fixedRate = 30000)
    private void deleteUnpaidOrders() {
        LocalDateTime tenMinutesAgo = LocalDateTime.now().minusMinutes(deletionDelay);
        List<Order> unpaidOrders = orderRepository
                .findByStateAndDateOfOrderBefore(EState.WAITING_FOR_PAYMENT, tenMinutesAgo);

        for (Order order : unpaidOrders) {
            restoreProductQuantities(order);
            orderRepository.delete(order);
        }
    }

    private void restoreProductQuantities(Order order) {
        for (OrderLine orderLine : order.getOrderLines()) {
            Product product = orderLine.getProduct();
            int returnedQuantity = orderLine.getQuantity();
            productService.changeQuantity(product.getId(), returnedQuantity);
        }
    }

    private Order getOrderForUser(User user) {
        return orderRepository.findOrderByUser(user);
    }
}

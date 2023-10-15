package com.dmtryii.internetshop.controller;

import com.dmtryii.internetshop.dto.OrderDTO;
import com.dmtryii.internetshop.dto.request.AddProductOrderRequest;
import com.dmtryii.internetshop.dto.response.OrderLineResponse;
import com.dmtryii.internetshop.model.Order;
import com.dmtryii.internetshop.model.OrderLine;
import com.dmtryii.internetshop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@RestController
public class OrderController {

    private final OrderService orderService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<OrderLineResponse> addProduct(@RequestBody AddProductOrderRequest request,
                                                        Principal principal) {
        OrderLine response = orderService.addProduct(
                request.getProductId(),
                request.getQuantity(),
                principal
        );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(map(response));
    }

    @PatchMapping("/pay")
    public ResponseEntity<OrderDTO> pay(Principal principal) {
        OrderDTO response = map(orderService.pay(principal));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    private OrderLineResponse map(OrderLine orderLine) {
        return modelMapper.map(orderLine, OrderLineResponse.class);
    }

    private OrderDTO map(Order order) {
        return modelMapper.map(order, OrderDTO.class);
    }
}

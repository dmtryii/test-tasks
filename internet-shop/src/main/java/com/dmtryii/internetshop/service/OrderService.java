package com.dmtryii.internetshop.service;

import com.dmtryii.internetshop.model.Order;
import com.dmtryii.internetshop.model.OrderLine;

import java.security.Principal;

public interface OrderService {
    Order create(Principal principal);
    OrderLine addProduct(Long productId, Integer quantity, Principal principal);
    Order pay(Principal principal);
}

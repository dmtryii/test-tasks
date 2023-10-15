package com.dmtryii.internetshop.repository;

import com.dmtryii.internetshop.model.Order;
import com.dmtryii.internetshop.model.OrderLine;
import com.dmtryii.internetshop.model.keys.OrderLineKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, OrderLineKey> {
}

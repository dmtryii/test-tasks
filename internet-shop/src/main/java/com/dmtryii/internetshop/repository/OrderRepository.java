package com.dmtryii.internetshop.repository;

import com.dmtryii.internetshop.model.Order;
import com.dmtryii.internetshop.model.User;
import com.dmtryii.internetshop.model.enums.EState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findOrderByUser(User user);
    List<Order> findByStateAndDateOfOrderBefore(EState state, LocalDateTime time);
}

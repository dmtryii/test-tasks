package com.dmtryii.internetshop.model;

import com.dmtryii.internetshop.model.keys.OrderLineKey;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class OrderLine {

    @EmbeddedId
    private OrderLineKey OrderLineId = new OrderLineKey();

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    @Min(value = 1, message = "The amount must be greater than zero")
    private Integer quantity;

    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    private LocalDate timeOfAddition;

    public OrderLine(Order order, Product product, Integer quantity) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        timeOfAddition = LocalDate.now();
    }
}

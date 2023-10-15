package com.dmtryii.internetshop.model;

import com.dmtryii.internetshop.annotations.Price;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull
    @NotEmpty(message = "product name should not be empty")
    private String name;

    @Column(nullable = false)
    @NotNull
    @Price
    private BigDecimal price;

    @Column(columnDefinition = "text")
    private String description;

    @Column(nullable = false)
    private Integer quantity;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<OrderLine> orderLines;

    public Product() {
        quantity = 0;
    }
}

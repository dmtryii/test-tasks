package com.dmtryii.internetshop.dto.response;

import com.dmtryii.internetshop.dto.OrderDTO;
import com.dmtryii.internetshop.dto.ProductDTO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderLineResponse {
    private OrderDTO order;
    private ProductDTO product;
    private Integer quantity;
    private LocalDateTime localDateTime;
}

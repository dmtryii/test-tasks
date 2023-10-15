package com.dmtryii.internetshop.dto.request;

import lombok.Data;

@Data
public class AddProductOrderRequest {
    private Long productId;
    private Integer quantity;
}

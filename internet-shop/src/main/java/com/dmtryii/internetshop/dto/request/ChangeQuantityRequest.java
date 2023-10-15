package com.dmtryii.internetshop.dto.request;

import lombok.Data;

@Data
public class ChangeQuantityRequest {
    private Long productId;
    private Integer quantity;
}

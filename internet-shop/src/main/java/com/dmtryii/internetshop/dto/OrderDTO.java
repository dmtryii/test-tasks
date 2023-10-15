package com.dmtryii.internetshop.dto;

import com.dmtryii.internetshop.model.enums.EState;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDTO {
    private Long id;
    private LocalDateTime dateOfOrder;
    private EState state;
}

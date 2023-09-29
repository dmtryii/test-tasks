package com.dmtryii.usersystem.exception.handle_exception;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ErrorObject {
    private Date timestamp;
    private Integer status;
    private String error;
    private String path;
}

package com.dmtryii.usersystem.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DataRangeDTO {
    @Past(message = "from date must be in the past")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate from;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate to;
}

package com.dmtryii.usersystem.dto.request;

import com.dmtryii.usersystem.annotation.PhoneNumber;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RegisterUserDTO {
    @Email(message = "Please provide a valid email address")
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Email is required")
    private String firstName;
    @NotBlank(message = "Last name is required")
    private String lastName;
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;
    private String address;
    @PhoneNumber
    private String phone;
}

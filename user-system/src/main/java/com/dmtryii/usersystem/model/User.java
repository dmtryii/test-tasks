package com.dmtryii.usersystem.model;

import com.dmtryii.usersystem.annotation.PhoneNumber;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotEmpty(message = "Email cannot be empty")
    @Column(nullable = false, unique = true)
    private String email;

    @NotEmpty(message = "FirstName cannot be empty")
    @Column(nullable = false)
    private String firstName;

    @NotEmpty(message = "LastName cannot be empty")
    @Column(nullable = false)
    private String lastName;

    @Past(message = "Birth date must be in the past")
    @NotEmpty(message = "BirthDate cannot be empty")
    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate birthDate;

    private String address;

    @PhoneNumber(message = "Phone is not valid")
    private String phone;

    public User(String email, String firstName, String lastName, LocalDate birthDate) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }
}

package com.dmtryii.internetshop.model;

import com.dmtryii.internetshop.model.enums.ERole;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, updatable = false)
    @NotEmpty(message = "username should not be empty")
    @Size(min = 2, max = 30, message = "username should be between 2 and 30 characters")
    private String username;

    @Email
    @Column(unique = true)
    @NotEmpty(message = "username should not be empty")
    private String email;

    @Column(length = 3000)
    @NotEmpty(message = "password should not be empty")
    private String password;

    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDateTime createData;

    @ElementCollection(targetClass = ERole.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"))
    private Set<ERole> roles;

    @PrePersist
    protected void onCreate() {
        this.createData = LocalDateTime.now();
    }
}

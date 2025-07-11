package com.wsd.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wsd.ecommerce.constant.UserType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Table(
        name = "users",
        indexes = {
                @Index(name = "idx_user_type", columnList = "userType")
        }
)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Getter
@Setter
public class User extends Audit<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    private UserType userType;
}

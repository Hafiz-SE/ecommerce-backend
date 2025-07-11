package com.wsd.ecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Table(name = "products")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Getter
@Setter
public class Product extends Audit<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Max(value = 100, message = "Name cannot be larger than 100 characters")
    @NotBlank(message = "Name cannot be blank")
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank(message = "Description cannot be blank")
    @Column(nullable = false, columnDefinition = "text")
    private String description;

    @NotNull(message = "price cannot be null")
    @Column(nullable = false)
    private BigDecimal price;
}

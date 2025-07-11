package com.wsd.ecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Table(name = "orders",
        indexes = {
                @Index(name = "idx_orders_user_id", columnList = "user_id")
        })
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Getter
@Setter
public class Order extends Audit<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull(message = "total price cannot be null")
    @Column(nullable = false)
    private BigDecimal totalPrice;
}

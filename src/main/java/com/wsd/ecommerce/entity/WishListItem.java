package com.wsd.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Table(name = "wish_list_items",
        indexes = {@Index(name = "user_index", columnList = "user_id")},
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uc_user_product",
                        columnNames = {"user_id", "product_id"}
                )
        })
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Getter
@Setter
public class WishListItem extends Audit<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}

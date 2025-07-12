package com.wsd.ecommerce.repository;

import com.wsd.ecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAll(Pageable pageable);

    @Query(value = """
            SELECT p.*
            FROM products p
            JOIN (
                SELECT oi.product_id
                FROM order_items oi
                GROUP BY oi.product_id
                ORDER BY SUM(oi.price_at_purchase_time * oi.quantity) DESC
                LIMIT 5
            ) top ON p.id = top.product_id
            """, nativeQuery = true)
    List<Product> findTop5SellingProductsByAmount();

    @Query(value = """
            SELECT p.*
            FROM products p
            JOIN (
                SELECT oi.product_id
                FROM order_items oi
                WHERE oi.created_at >= :startDate AND oi.created_at <= :endDate
                GROUP BY oi.product_id
                ORDER BY SUM(oi.quantity) DESC
                LIMIT 5
            ) top ON p.id = top.product_id
            """, nativeQuery = true)
    List<Product> findTop5SellingProductsByCountLastMonth(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}

package com.wsd.ecommerce.repository;

import com.wsd.ecommerce.dto.DailySalesDto;
import com.wsd.ecommerce.entity.Order;
import com.wsd.ecommerce.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByUser(User user, Pageable pageable);

    @Query("""
            SELECT DATE(o.createdAt) as orderDate,
                   SUM(o.totalPrice) as totalSales
            FROM Order o
            WHERE o.createdAt BETWEEN :startDate AND :endDate
            GROUP BY DATE(o.createdAt)
            ORDER BY SUM(o.totalPrice) DESC
            """)
    DailySalesDto findMaxSaleDay(@Param("startDate") LocalDate startDate,
                                 @Param("endDate") LocalDate endDate);
}

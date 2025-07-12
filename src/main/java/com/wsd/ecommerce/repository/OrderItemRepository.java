package com.wsd.ecommerce.repository;

import com.wsd.ecommerce.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrderId(Long orderId);

    @Query(value = """
            SELECT COALESCE(SUM(oi.price_at_purchase_time * oi.quantity), 0)
            FROM order_items oi
            WHERE oi.created_at::date = CURRENT_DATE
            """, nativeQuery = true)
    BigDecimal findTotalSaleAmountOfToday();
}

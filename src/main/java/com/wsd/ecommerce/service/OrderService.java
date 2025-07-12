package com.wsd.ecommerce.service;

import com.wsd.ecommerce.entity.Order;
import com.wsd.ecommerce.entity.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    Page<Order> getOrdersOfCurrentUser(Pageable pageable);

    List<OrderItem> getOrderItemsOfAnOrder(Long orderId);

    Order createOrder(List<Long> productIds);

    LocalDate getMaxSaleDateBetween(LocalDate startDate, LocalDate endDate);

    BigDecimal getCurrentDateSaleAmount();
}

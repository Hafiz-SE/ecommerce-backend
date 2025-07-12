package com.wsd.ecommerce.service;

import com.wsd.ecommerce.dto.PaginationArgs;
import com.wsd.ecommerce.dto.request.CreateOrderRequest;
import com.wsd.ecommerce.entity.Order;
import com.wsd.ecommerce.entity.OrderItem;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    Page<Order> getOrdersOfCurrentUser(PaginationArgs paginationArgs);

    List<OrderItem> getOrderItemsOfAnOrder(Long orderId);

    Order createOrder(CreateOrderRequest createOrderRequest);

    LocalDate getMaxSaleDateBetween(LocalDate startDate, LocalDate endDate);

    BigDecimal getCurrentDateSaleAmount();
}

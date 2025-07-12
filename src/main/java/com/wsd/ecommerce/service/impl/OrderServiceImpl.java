package com.wsd.ecommerce.service.impl;

import com.wsd.ecommerce.entity.Order;
import com.wsd.ecommerce.entity.OrderItem;
import com.wsd.ecommerce.repository.OrderItemRepository;
import com.wsd.ecommerce.repository.OrderRepository;
import com.wsd.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public Page<Order> getOrdersOfCurrentUser(Pageable pageable) {
        return null;
    }

    @Override
    public List<OrderItem> getOrderItemsOfAnOrder(Long orderId) {
        return List.of();
    }

    @Override
    public Order createOrder(List<Long> productIds) {
        return null;
    }

    @Override
    public LocalDate getMaxSaleDateBetween(LocalDate startDate, LocalDate endDate) {
        return null;
    }

    @Override
    public BigDecimal getCurrentDateSaleAmount() {
        return null;
    }
}

package com.wsd.ecommerce.service.impl;

import com.wsd.ecommerce.dto.DailySalesDto;
import com.wsd.ecommerce.dto.request.CreateOrderRequest;
import com.wsd.ecommerce.dto.request.OrderItemRequest;
import com.wsd.ecommerce.entity.Order;
import com.wsd.ecommerce.entity.OrderItem;
import com.wsd.ecommerce.entity.Product;
import com.wsd.ecommerce.entity.User;
import com.wsd.ecommerce.exception.ApplicationException;
import com.wsd.ecommerce.repository.OrderItemRepository;
import com.wsd.ecommerce.repository.OrderRepository;
import com.wsd.ecommerce.repository.ProductRepository;
import com.wsd.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.wsd.ecommerce.utility.UserUtility.getCurrentUser;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    @Override
    public Page<Order> getOrdersOfCurrentUser(Pageable pageable) {
        User user = getCurrentUser();
        return orderRepository.findByUser(user, pageable);
    }

    @Override
    public List<OrderItem> getOrderItemsOfAnOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ApplicationException("Order not found"));
        return orderItemRepository.findByOrder(order);
    }

    @Override
    @Transactional
    public Order createOrder(CreateOrderRequest createOrderRequest) {
        User user = getCurrentUser();

        Set<Long> productIds = createOrderRequest.orderItems().stream()
                .map(OrderItemRequest::productId)
                .collect(Collectors.toSet());

        List<Product> products = productRepository.findAllById(productIds);
        if (products.size() != productIds.size()) {
            throw new ApplicationException("One or more products not found");
        }

        Map<Long, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getId, p -> p));

        List<OrderItem> orderItems = createOrderRequest.orderItems().stream()
                .map(item -> {
                    Product product = productMap.get(item.productId());
                    return OrderItem.builder()
                            .product(product)
                            .quantity(item.quantity())
                            .priceAtPurchaseTime(product.getPrice())
                            .build();
                })
                .collect(Collectors.toList());

        BigDecimal totalPrice = orderItems.stream()
                .map(i -> i.getPriceAtPurchaseTime().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = Order.builder()
                .user(user)
                .totalPrice(totalPrice)
                .build();
        order = orderRepository.save(order);

        Order finalOrder = order;

        orderItems.forEach(i -> i.setOrder(finalOrder));
        orderItemRepository.saveAll(orderItems);
        return order;
    }

    @Override
    public LocalDate getMaxSaleDateBetween(LocalDate startDate, LocalDate endDate) {
        DailySalesDto dailySalesDto = orderRepository.findMaxSaleDay(startDate, endDate);

        log.info("Max Sale Between {} and {} is on {} and sale amount is {}",
                startDate, endDate, dailySalesDto.getDate(), dailySalesDto.getSalesAmount());

        return dailySalesDto.getDate();
    }

    @Override
    public BigDecimal getCurrentDateSaleAmount() {
        return orderItemRepository.findTotalSaleAmountOfToday();
    }
}

package com.wsd.ecommerce.controller;

import com.wsd.ecommerce.dto.PaginationArgs;
import com.wsd.ecommerce.dto.request.CreateOrderRequest;
import com.wsd.ecommerce.entity.Order;
import com.wsd.ecommerce.entity.OrderItem;
import com.wsd.ecommerce.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public void createOrder(@RequestBody @Valid CreateOrderRequest orderRequest) {

        orderService.createOrder(orderRequest);
    }

    @GetMapping
    public Page<Order> getCurrentUserOrders(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        return orderService.getOrdersOfCurrentUser(PaginationArgs.builder().pageNo(pageNo).pageSize(pageSize).build());
    }

    @GetMapping("id/{orderId}/order-item")
    public List<OrderItem> getOrderItems(@PathVariable(value = "orderId") Long orderId) {

        return orderService.getOrderItemsOfAnOrder(orderId);
    }

    @GetMapping("/sales")
    @PreAuthorize("hasAuthority('ADMIN')")
    public LocalDate getSalesBetweenDates(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {

        return orderService.getMaxSaleDateBetween(startDate, endDate);
    }

    @GetMapping("/sales/today")
    @PreAuthorize("hasAuthority('ADMIN')")
    public BigDecimal getSaleAmountForToday() {

        return orderService.getCurrentDateSaleAmount();
    }
}

package com.wsd.ecommerce.service.impl;

import com.wsd.ecommerce.constant.UserType;
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
import com.wsd.ecommerce.security.custom.user.CustomUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private User user;

    @BeforeEach
    void setUp() {
        user = getSampleUser();
    }

    @Test
    void shouldGetOrdersOfCurrentUser() {
        mockSecurityContext(user);
        Page<Order> orders = new PageImpl<>(List.of(Order.builder().user(user).build()));
        when(orderRepository.findByUser(eq(user), any(PageRequest.class))).thenReturn(orders);

        Page<Order> result = orderService.getOrdersOfCurrentUser(PageRequest.of(0, 10));

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getUser().getId()).isEqualTo(user.getId());
    }

    @Test
    void shouldReturnOrderItemsOfAnOrder() {
        Order order = Order.builder().id(1L).user(user).build();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderItemRepository.findByOrder(order)).thenReturn(List.of(new OrderItem()));

        List<OrderItem> result = orderService.getOrderItemsOfAnOrder(1L);

        assertThat(result).hasSize(1);
    }

    @Test
    void shouldThrowWhenOrderNotFound() {
        when(orderRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.getOrderItemsOfAnOrder(99L))
                .isInstanceOf(ApplicationException.class)
                .hasMessageContaining("Order not found");
    }

    @Test
    void shouldCreateOrderSuccessfully() {
        mockSecurityContext(user);

        Product product = Product.builder().id(1L).price(BigDecimal.valueOf(100)).build();
        Set<OrderItemRequest> items = Set.of(new OrderItemRequest(product.getId(), 2));
        CreateOrderRequest request = new CreateOrderRequest(items);

        when(productRepository.findAllById(Set.of(1L))).thenReturn(List.of(product));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Order result = orderService.createOrder(request);

        assertThat(result).isNotNull();
        assertThat(result.getTotalPrice()).isEqualByComparingTo(BigDecimal.valueOf(200));
    }

    @Test
    void shouldThrowWhenProductNotFoundDuringOrderCreation() {
        mockSecurityContext(user);
        
        CreateOrderRequest request = new CreateOrderRequest(Set.of(new OrderItemRequest(999L, 1)));
        when(productRepository.findAllById(Set.of(999L))).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> orderService.createOrder(request))
                .isInstanceOf(ApplicationException.class)
                .hasMessageContaining("products not found");
    }

    @Test
    void shouldGetMaxSaleDateBetweenRange() {
        LocalDate start = LocalDate.now().minusDays(7);
        LocalDate end = LocalDate.now();
        DailySalesDto dto = new DailySalesDto(start.plusDays(3), 3000.00);
        when(orderRepository.findMaxSaleDay(start, end)).thenReturn(dto);

        LocalDate result = orderService.getMaxSaleDateBetween(start, end);

        assertThat(result).isEqualTo(dto.getDate());
    }

    @Test
    void shouldReturnTodaySaleAmount() {
        BigDecimal todaySale = BigDecimal.valueOf(1500);
        when(orderItemRepository.findTotalSaleAmountOfToday()).thenReturn(todaySale);

        BigDecimal result = orderService.getCurrentDateSaleAmount();

        assertThat(result).isEqualByComparingTo(todaySale);
    }

    private void mockSecurityContext(User user) {
        Authentication authentication = mock(Authentication.class);
        CustomUserDetails customUserDetails = CustomUserDetails.create(user);
        when(authentication.getPrincipal()).thenReturn(customUserDetails); // ✅

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);
    }

    private User getSampleUser() {
        return User.builder()
                .id(1L)
                .email("customer@example.com")
                .userType(UserType.CUSTOMER)
                .build();
    }

    private Product getSampleProduct() {
        return Product.builder()
                .id(100L)
                .price(BigDecimal.valueOf(100))
                .build();
    }
}
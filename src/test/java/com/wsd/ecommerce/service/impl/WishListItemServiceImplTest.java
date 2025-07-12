package com.wsd.ecommerce.service.impl;

import com.wsd.ecommerce.dto.PaginationArgs;
import com.wsd.ecommerce.entity.Product;
import com.wsd.ecommerce.entity.User;
import com.wsd.ecommerce.entity.WishListItem;
import com.wsd.ecommerce.exception.ApplicationException;
import com.wsd.ecommerce.repository.WishListItemRepository;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class WishListItemServiceImplTest {

    @Mock
    private WishListItemRepository wishListItemRepository;

    @InjectMocks
    private WishListItemServiceImpl wishListItemService;

    @Test
    void shouldAddWishListItemSuccessfully() {
        User user = getSampleUser();
        Product product = getSampleProduct();
        WishListItem savedItem = getSampleWishListItem(user, product);

        mockSecurityContext(user);
        when(wishListItemRepository.save(any(WishListItem.class))).thenReturn(savedItem);

        WishListItem result = wishListItemService.add(product.getId());

        assertThat(result).isNotNull();
        assertThat(result.getUser()).isEqualTo(user);
        assertThat(result.getProduct().getId()).isEqualTo(product.getId());
    }

    @Test
    void shouldThrowExceptionWhenAddingDuplicateWishListItem() {
        User user = getSampleUser();
        Product product = getSampleProduct();

        mockSecurityContext(user);
        when(wishListItemRepository.save(any(WishListItem.class)))
                .thenThrow(new DataIntegrityViolationException("Unique constraint violation"));

        assertThatThrownBy(() -> wishListItemService.add(product.getId()))
                .isInstanceOf(ApplicationException.class);
    }

    @Test
    void shouldRemoveWishListItemById() {
        Long id = 1L;
        assertThatCode(() -> wishListItemService.remove(id)).doesNotThrowAnyException();
        verify(wishListItemRepository).deleteById(id);
    }

    @Test
    void shouldGetWishListItemsForCurrentUserPaginated() {
        User user = getSampleUser();
        Page<WishListItem> page = new PageImpl<>(Collections.singletonList(getSampleWishListItem(user, getSampleProduct())));

        mockSecurityContext(user);
        when(wishListItemRepository.findByUser(eq(user), any(PageRequest.class))).thenReturn(page);

        Page<WishListItem> result = wishListItemService.get(new PaginationArgs(0, 10));

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getUser()).isEqualTo(user);
    }

    private void mockSecurityContext(User user) {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(user);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);
    }

    private User getSampleUser() {
        return User.builder()
                .id(1L)
                .email("customer@example.com")
                .build();
    }

    private Product getSampleProduct() {
        return Product.builder()
                .id(100L)
                .name("Sample Product")
                .build();
    }

    private WishListItem getSampleWishListItem(User user, Product product) {
        return WishListItem.builder()
                .id(1L)
                .user(user)
                .product(product)
                .build();
    }
}
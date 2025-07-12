package com.wsd.ecommerce.service.impl;

import com.wsd.ecommerce.dto.PaginationArgs;
import com.wsd.ecommerce.dto.request.CreateProductRequest;
import com.wsd.ecommerce.dto.request.UpdateProductRequest;
import com.wsd.ecommerce.entity.Product;
import com.wsd.ecommerce.repository.ProductRepository;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void shouldCreateProductSuccessfully() {
        CreateProductRequest request = new CreateProductRequest("Test Product", "Nice Product", BigDecimal.valueOf(100L));
        Product savedProduct = Product.builder().id(1L).name("Test Product").price(BigDecimal.valueOf(100)).build();

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        Product result = productService.createProduct(request);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Test Product");
    }

    @Test
    void shouldUpdateProductSuccessfully() {
        UpdateProductRequest request = new UpdateProductRequest("tester", "Updated Product", BigDecimal.valueOf(150), 1L);
        Product existingProduct = Product.builder().id(1L).name("Old Product").price(BigDecimal.valueOf(100)).build();
        Product updatedProduct = Product.builder().id(1L).name("Updated Product").price(BigDecimal.valueOf(150)).build();

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        Product result = productService.updateProduct(request);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Updated Product");
    }

    @Test
    void shouldGetPaginatedProducts() {
        Page<Product> page = new PageImpl<>(Collections.singletonList(getSampleProduct()));
        when(productRepository.findAll(any(PageRequest.class))).thenReturn(page);

        Page<Product> result = productService.getProducts(new PaginationArgs(0, 10));

        assertThat(result.getContent()).hasSize(1);
    }

    @Test
    void shouldDeleteProductSuccessfully() {
        assertThatCode(() -> productService.deleteProduct("1")).doesNotThrowAnyException();
        verify(productRepository).deleteById(1L);
    }

    @Test
    void shouldReturnTop5ProductsOfLastMonthByCount() {
        List<Product> products = List.of(getSampleProduct());
        when(productRepository.findTop5SellingProductsByCountLastMonth(LocalDate.now(), LocalDate.now().plusMonths(1))).thenReturn(products);

        List<Product> result = productService.getTop5ProductsOfLastMonthByCount();

        assertThat(result).hasSize(1);
    }

    @Test
    void shouldReturnTop5AllTimeProductsBySaleAmount() {
        List<Product> products = List.of(getSampleProduct());
        when(productRepository.findTop5SellingProductsByAmount()).thenReturn(products);

        List<Product> result = productService.getTop5AllTimeProductsBySaleAmount();

        assertThat(result).hasSize(1);
    }

    private Product getSampleProduct() {
        return Product.builder()
                .id(1L)
                .name("Sample Product")
                .price(BigDecimal.valueOf(100))
                .build();
    }

}
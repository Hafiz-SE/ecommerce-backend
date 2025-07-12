package com.wsd.ecommerce.service.impl;

import com.wsd.ecommerce.dto.PaginationArgs;
import com.wsd.ecommerce.dto.request.CreateProductRequest;
import com.wsd.ecommerce.dto.request.UpdateProductRequest;
import com.wsd.ecommerce.entity.Product;
import com.wsd.ecommerce.exception.ApplicationException;
import com.wsd.ecommerce.repository.ProductRepository;
import com.wsd.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public Product createProduct(CreateProductRequest createProductRequest) {
        Product product = Product.builder()
                .name(createProductRequest.name())
                .description(createProductRequest.description())
                .price(createProductRequest.price())
                .build();
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(UpdateProductRequest updateProductRequest) {
        Product existing = productRepository.findById(updateProductRequest.productId())
                .orElseThrow(() -> new ApplicationException("Product not found"));

        existing.setName(updateProductRequest.name());
        existing.setDescription(updateProductRequest.description());
        existing.setPrice(updateProductRequest.price());
        return productRepository.save(existing);
    }

    @Override
    public Page<Product> getProducts(PaginationArgs paginationArgs) {
        return productRepository.findAll(PageRequest.of(paginationArgs.getPageNo(), paginationArgs.getPageSize()));
    }

    @Override
    public void deleteProduct(String productId) {
        Long id = Long.parseLong(productId);
        if (!productRepository.existsById(id)) {
            throw new ApplicationException("Product not found");
        }
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getTop5ProductsOfLastMonthByCount() {
        LocalDate now = LocalDate.now();
        LocalDate startOfLastMonth = now.minusMonths(1).withDayOfMonth(1);
        LocalDate endOfLastMonth = now.withDayOfMonth(1).minusDays(1);

        return productRepository.findTop5SellingProductsByCountLastMonth(startOfLastMonth, endOfLastMonth);
    }

    @Override
    public List<Product> getTop5AllTimeProductsBySaleAmount() {
        return productRepository.findTop5SellingProductsByAmount();
    }
}

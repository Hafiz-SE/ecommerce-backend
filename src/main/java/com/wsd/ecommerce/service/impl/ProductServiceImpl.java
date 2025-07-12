package com.wsd.ecommerce.service.impl;

import com.wsd.ecommerce.dto.PaginationArgs;
import com.wsd.ecommerce.dto.request.CreateProductRequest;
import com.wsd.ecommerce.dto.request.UpdateProductRequest;
import com.wsd.ecommerce.entity.Product;
import com.wsd.ecommerce.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Override
    public Product createProduct(CreateProductRequest createProductRequest) {
        return null;
    }

    @Override
    public Product updateProduct(UpdateProductRequest updateProductRequest) {
        return null;
    }

    @Override
    public Page<Product> getProducts(PaginationArgs paginationArgs) {
        return null;
    }

    @Override
    public void deleteProduct(String productId) {

    }

    @Override
    public List<Product> getTop5ProductsOfLastMonthByCount() {
        return List.of();
    }

    @Override
    public List<Product> getTop5AllTimeProductsBySaleAmount() {
        return List.of();
    }
}

package com.wsd.ecommerce.service;


import com.wsd.ecommerce.dto.PaginationArgs;
import com.wsd.ecommerce.dto.request.CreateProductRequest;
import com.wsd.ecommerce.dto.request.UpdateProductRequest;
import com.wsd.ecommerce.entity.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    Product createProduct(CreateProductRequest createProductRequest);

    Product updateProduct(UpdateProductRequest updateProductRequest);

    Page<Product> getProducts(PaginationArgs paginationArgs);

    void deleteProduct(String productId);

    List<Product> getTop5ProductsOfLastMonthByCount();

    List<Product> getTop5AllTimeProductsBySaleAmount();
}

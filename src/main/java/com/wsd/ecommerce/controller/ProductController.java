package com.wsd.ecommerce.controller;

import com.wsd.ecommerce.dto.PaginationArgs;
import com.wsd.ecommerce.dto.request.CreateProductRequest;
import com.wsd.ecommerce.dto.request.UpdateProductRequest;
import com.wsd.ecommerce.entity.Product;
import com.wsd.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public Product createProduct(@RequestBody @Valid CreateProductRequest request) {
        return productService.createProduct(request);
    }

    @PutMapping
    public Product updateProduct(@RequestBody @Valid UpdateProductRequest request) {
        return productService.updateProduct(request);
    }

    @GetMapping
    public Page<Product> getProducts(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                     @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return productService.getProducts(new PaginationArgs(pageNo, pageSize));
    }

    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable String productId) {
        productService.deleteProduct(productId);
    }

    @GetMapping("/top5/monthly")
    public List<Product> getTop5LastMonth() {
        return productService.getTop5ProductsOfLastMonthByCount();
    }

    @GetMapping("/top5/alltime")
    public List<Product> getTop5AllTime() {
        return productService.getTop5AllTimeProductsBySaleAmount();
    }
}

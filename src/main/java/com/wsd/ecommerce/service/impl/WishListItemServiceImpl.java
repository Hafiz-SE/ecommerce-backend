package com.wsd.ecommerce.service.impl;

import com.wsd.ecommerce.dto.PaginationArgs;
import com.wsd.ecommerce.entity.Product;
import com.wsd.ecommerce.entity.User;
import com.wsd.ecommerce.entity.WishListItem;
import com.wsd.ecommerce.exception.ApplicationException;
import com.wsd.ecommerce.repository.ProductRepository;
import com.wsd.ecommerce.repository.WishListItemRepository;
import com.wsd.ecommerce.service.WishListItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import static com.wsd.ecommerce.utility.UserUtility.getCurrentUser;

@Service
@RequiredArgsConstructor
public class WishListItemServiceImpl implements WishListItemService {

    private final WishListItemRepository wishListItemRepository;
    private final ProductRepository productRepository;

    @Override
    public WishListItem add(Long productId) {
        User user = getCurrentUser();

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ApplicationException("Product not found with id: " + productId));

        WishListItem item = WishListItem.builder()
                .user(user)
                .product(product)
                .build();

        try {
            return wishListItemRepository.save(item);
        } catch (DataIntegrityViolationException exception) {
            throw new ApplicationException("You have already added this product in wishlist");
        }
    }

    @Override
    public void remove(Long id) {
        wishListItemRepository.deleteById(id);
    }

    @Override
    public Page<WishListItem> get(PaginationArgs paginationArgs) {
        User user = getCurrentUser();

        return wishListItemRepository.findByUser(user,
                PageRequest.of(paginationArgs.getPageNo(), paginationArgs.getPageSize()));
    }
}

package com.wsd.ecommerce.service;

import com.wsd.ecommerce.dto.PaginationArgs;
import com.wsd.ecommerce.entity.WishListItem;
import org.springframework.data.domain.Page;

public interface WishListService {
    WishListItem add(Long productId);

    void remove(Long id);

    Page<WishListItem> get(PaginationArgs paginationArgs);
}

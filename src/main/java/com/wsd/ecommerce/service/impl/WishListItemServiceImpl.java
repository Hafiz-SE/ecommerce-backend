package com.wsd.ecommerce.service.impl;

import com.wsd.ecommerce.dto.PaginationArgs;
import com.wsd.ecommerce.entity.WishListItem;
import com.wsd.ecommerce.service.WishListItemService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class WishListItemServiceImpl implements WishListItemService {
    @Override
    public WishListItem add(Long productId) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public Page<WishListItem> get(PaginationArgs paginationArgs) {
        return null;
    }
}

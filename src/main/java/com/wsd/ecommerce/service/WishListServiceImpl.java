package com.wsd.ecommerce.service;

import com.wsd.ecommerce.dto.PaginationArgs;
import com.wsd.ecommerce.entity.WishListItem;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class WishListServiceImpl implements WishListService {
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

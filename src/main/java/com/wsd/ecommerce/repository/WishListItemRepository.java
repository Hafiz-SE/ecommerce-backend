package com.wsd.ecommerce.repository;

import com.wsd.ecommerce.entity.User;
import com.wsd.ecommerce.entity.WishListItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListItemRepository extends JpaRepository<WishListItem, Long> {
    Page<WishListItem> findByUser(User user, Pageable pageable);
}

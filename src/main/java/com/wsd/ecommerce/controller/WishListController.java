package com.wsd.ecommerce.controller;

import com.wsd.ecommerce.dto.PaginationArgs;
import com.wsd.ecommerce.dto.request.WishListAddRequest;
import com.wsd.ecommerce.entity.WishListItem;
import com.wsd.ecommerce.service.WishListItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("wishlist")
@RequiredArgsConstructor
public class WishListController {

    private final WishListItemService wishListItemService;

    @PostMapping
    public WishListItem addWishList(@RequestBody @Valid WishListAddRequest wishListAddRequest) {
        return wishListItemService.add(wishListAddRequest.productId());
    }

    @GetMapping
    public Page<WishListItem> getPaginatedWishList(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                                   @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        return wishListItemService.get(PaginationArgs.builder().pageSize(pageSize).pageNo(pageNo).build());
    }

    @DeleteMapping("/id/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFromWishList(@PathVariable Long id) {

        wishListItemService.remove(id);
    }
}

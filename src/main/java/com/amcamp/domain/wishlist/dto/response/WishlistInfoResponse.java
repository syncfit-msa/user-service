package com.amcamp.domain.wishlist.dto.response;

import com.amcamp.domain.wishlist.domain.Wishlist;

public record WishlistInfoResponse(Long id, String title, String imageUrl) {
    public static WishlistInfoResponse from(Wishlist wishlist) {
        return new WishlistInfoResponse(wishlist.getId(), wishlist.getTitle(), wishlist.getImageUrl());
    }
}
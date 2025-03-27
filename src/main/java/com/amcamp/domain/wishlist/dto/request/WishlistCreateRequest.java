package com.amcamp.domain.wishlist.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record WishlistCreateRequest(String title, MultipartFile image) {
}
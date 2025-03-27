package com.amcamp.domain.wishlist.application;

import com.amcamp.domain.image.application.ImageService;
import com.amcamp.domain.member.domain.Member;
import com.amcamp.domain.track.dao.TrackRepository;
import com.amcamp.domain.wishlist.dao.WishlistRepository;
import com.amcamp.domain.wishlist.domain.Wishlist;
import com.amcamp.domain.wishlist.dto.response.WishlistInfoResponse;
import com.amcamp.global.error.exception.CustomException;
import com.amcamp.global.error.exception.ErrorCode;
import com.amcamp.global.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final MemberUtil memberUtil;
    private final ImageService imageService;
    private final TrackRepository trackRepository;

    public void createWishlist(String title, MultipartFile file) {
        Member member = memberUtil.getCurrentMember();

        String imageUrl = imageService.uploadInitWishlistImage(file);

        Wishlist wishlist = wishlistRepository.save(Wishlist.createWishlist(member, title, imageUrl));
        imageService.storeImageInfo(imageUrl, wishlist.getId().toString());

        new WishlistInfoResponse(wishlist.getId(), wishlist.getTitle(), wishlist.getImageUrl());
    }

    public void deleteWishlist(Long wishlistId) {
        Member currentMember = memberUtil.getCurrentMember();
        Wishlist wishlist = findWishlistById(wishlistId);

        validateOwnership(wishlist, currentMember);

        wishlistRepository.delete(wishlist);
    }

    public List<WishlistInfoResponse> findAllWishlist() {
        Member currentMember = memberUtil.getCurrentMember();

        return wishlistRepository.findByMember(currentMember)
                .stream()
                .map(WishlistInfoResponse::from)
                .toList();
    }

    public void editWishlist(Long wishlistId, String title, MultipartFile file) {
        Member currentMember = memberUtil.getCurrentMember();
        Wishlist wishlist = findWishlistById(wishlistId);

        validateOwnership(wishlist, currentMember);

        if (title != null && !title.isBlank()) {
            wishlist.updateTitle(title);
        }

        if (file != null && !file.isEmpty()) {
            String imageUrl = imageService.uploadInitWishlistImage(file);
            wishlist.updateImageUrl(imageUrl);
            imageService.storeImageInfo(imageUrl, wishlist.getId().toString());
        }
    }

    private void validateOwnership(Wishlist wishlist, Member currentMember) {
        if (!wishlist.getMember().equals(currentMember)) {
            throw new CustomException(ErrorCode.WISHLIST_MEMBER_MISMATCH);
        }
    }

    private Wishlist findWishlistById(Long wishlistId) {
        return wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new CustomException((ErrorCode.WISHLIST_NOT_FOUND)));
    }
}


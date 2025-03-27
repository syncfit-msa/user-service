package com.amcamp.domain.track.application;

import com.amcamp.domain.member.domain.Member;
import com.amcamp.domain.track.dao.TrackRepository;
import com.amcamp.domain.track.domain.Track;
import com.amcamp.domain.track.dto.request.TrackCreateRequest;
import com.amcamp.domain.track.dto.response.TrackInfoResponse;
import com.amcamp.domain.wishlist.dao.WishlistRepository;
import com.amcamp.domain.wishlist.domain.Wishlist;
import com.amcamp.global.error.exception.CustomException;
import com.amcamp.global.error.exception.ErrorCode;
import com.amcamp.global.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class TrackService {

    private final MemberUtil memberUtil;
    private final WishlistRepository wishlistRepository;
    private final TrackRepository trackRepository;

    public void createTrack(TrackCreateRequest request) {
        final Member currentMember = memberUtil.getCurrentMember();
        final Wishlist wishlist = findWishlistById(request.wishlistId());

        validateWishlistMemberMismatch(wishlist, currentMember);

        trackRepository.save(
                Track.createTrack(
                        request.artistName(), request.title(), request.albumName(), request.imageUrl(), wishlist));
    }

    public void deleteTrack(Long trackId) {
        final Member currentMember = memberUtil.getCurrentMember();
        final Track track = findTrackById(trackId);

        validateTrackMemberMismatch(track, currentMember);

        trackRepository.deleteById(trackId);
    }

    @Transactional(readOnly = true)
    public List<TrackInfoResponse> findAllTrack(Long wishlistId) {
        final Member currentMember = memberUtil.getCurrentMember();
        final Wishlist wishlist = findWishlistById(wishlistId);

        validateWishlistMemberMismatch(wishlist, currentMember);

        return trackRepository.findAllByWishlistId(wishlist.getId())
                .stream()
                .map(TrackInfoResponse::from)
                .toList();
    }

    private Wishlist findWishlistById(Long wishlistId) {
        return wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new CustomException(ErrorCode.WISHLIST_NOT_FOUND));
    }

    private void validateWishlistMemberMismatch(Wishlist wishlist, Member member) {
        if (!wishlist.getMember().getId().equals(member.getId())) {
            throw new CustomException(ErrorCode.WISHLIST_MEMBER_MISMATCH);
        }
    }

    private Track findTrackById(Long trackId) {
        return trackRepository.findById(trackId)
                .orElseThrow(() -> new CustomException(ErrorCode.TRACK_NOT_FOUND));
    }

    private void validateTrackMemberMismatch(Track track, Member member) {
        if (!track.getWishlist().getMember().getId().equals(member.getId())) {
            throw new CustomException(ErrorCode.TRACK_MEMBER_MISMATCH);
        }
    }
}

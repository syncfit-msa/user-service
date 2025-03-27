package com.amcamp.domain.wishlist.domain;


import com.amcamp.domain.common.model.BaseTimeEntity;
import com.amcamp.domain.member.domain.Member;
import com.amcamp.domain.track.domain.Track;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Wishlist extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wishlist_id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "wishlist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Track> tracks = new ArrayList<>();


    @Builder(access = AccessLevel.PRIVATE)
    private Wishlist(Member member, String title, String imageUrl) {
        this.member = member;
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public static Wishlist createWishlist(Member member, String title, String imageUrl) {
        return Wishlist.builder().member(member).title(title).imageUrl(imageUrl).build();
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
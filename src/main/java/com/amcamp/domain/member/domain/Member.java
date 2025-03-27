package com.amcamp.domain.member.domain;

import com.amcamp.domain.common.model.BaseTimeEntity;
import com.amcamp.domain.wishlist.domain.Wishlist;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String nickname;

    private String profileImageUrl;

    @Embedded
    private OauthInfo oauthInfo;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Wishlist> wishlists = new ArrayList<>();

    @Builder(access = AccessLevel.PRIVATE)
    public Member(String nickname, String profileImageUrl, OauthInfo oauthInfo, MemberRole role) {
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.oauthInfo = oauthInfo;
        this.role = role;
    }

    public static Member createMember(String nickname, String profileImageUrl, OauthInfo oauthInfo) {
        return Member.builder()
                .nickname(nickname)
                .profileImageUrl(profileImageUrl)
                .oauthInfo(oauthInfo)
                .role(MemberRole.USER)
                .build();
    }
}

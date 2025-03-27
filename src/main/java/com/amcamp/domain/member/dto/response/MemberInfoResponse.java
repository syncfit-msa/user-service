package com.amcamp.domain.member.dto.response;

import com.amcamp.domain.member.domain.Member;
import com.amcamp.domain.member.domain.MemberRole;

public record MemberInfoResponse(
        Long memberId,
        String nickname,
        String profileImageUrl,
        MemberRole role) {
    public static MemberInfoResponse from(Member member) {
        return new MemberInfoResponse(
                member.getId(),
                member.getNickname(),
                member.getProfileImageUrl(),
                member.getRole());
    }
}
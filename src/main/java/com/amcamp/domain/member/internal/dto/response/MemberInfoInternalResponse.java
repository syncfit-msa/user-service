package com.amcamp.domain.member.internal.dto.response;

import com.amcamp.domain.member.domain.Member;

public record MemberInfoInternalResponse(Long memberId, String nickname) {
    public static MemberInfoInternalResponse from(Member member) {
        return new MemberInfoInternalResponse(
                member.getId(),
                member.getNickname());
    }
}

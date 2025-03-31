package com.amcamp.domain.member.application;

import com.amcamp.domain.auth.dao.RefreshTokenRepository;
import com.amcamp.domain.member.domain.Member;
import com.amcamp.domain.member.dto.response.MemberInfoResponse;
import com.amcamp.global.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberUtil memberUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    public void logoutMember() {
        Member currentMember = memberUtil.getCurrentMember();
        refreshTokenRepository
                .findById(currentMember.getId())
                .ifPresent(refreshTokenRepository::delete);
    }

    @Transactional(readOnly = true)
    public MemberInfoResponse getMemberInfo() {
        Member currentMember = memberUtil.getCurrentMember();
        return MemberInfoResponse.from(currentMember);
    }
}

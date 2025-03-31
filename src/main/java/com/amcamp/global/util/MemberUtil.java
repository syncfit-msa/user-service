package com.amcamp.global.util;

import com.amcamp.domain.member.dao.MemberRepository;
import com.amcamp.domain.member.domain.Member;
import com.amcamp.global.error.exception.CustomException;
import com.amcamp.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MemberUtil {

    private final MemberRepository memberRepository;

    public Member getCurrentMember() {
        log.info("Get current member");
        return memberRepository
                .findById(getCurrentMemberId())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }

    private Long getCurrentMemberId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            return Long.parseLong(authentication.getName());
        } catch (Exception e) {
            throw new CustomException(ErrorCode.AUTH_NOT_FOUND);
        }
    }
}
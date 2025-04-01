package com.amcamp.domain.member.api;

import com.amcamp.domain.member.application.MemberService;
import com.amcamp.domain.member.dto.response.MemberInfoResponse;
import com.amcamp.global.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final CookieUtil cookieUtil;

    @PostMapping("/logout")
    public ResponseEntity<Void> memberLogout(@RequestHeader("X-Member-Id") String memberId) {
        memberService.logoutMember(Long.parseLong(memberId));
        return ResponseEntity.ok().headers(cookieUtil.deleteRefreshTokenCookie()).build();
    }

    @GetMapping("/me")
    public MemberInfoResponse memberInfo(@RequestHeader("X-Member-Id") String memberId) {
        return memberService.getMemberInfo(Long.parseLong(memberId));
    }
}

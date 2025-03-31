package com.amcamp.domain.member.internal;

import com.amcamp.domain.member.domain.Member;
import com.amcamp.domain.member.dto.response.MemberInfoResponse;
import com.amcamp.domain.member.internal.dto.response.MemberInfoInternalResponse;
import com.amcamp.global.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/members")
@RequiredArgsConstructor
public class MemberInternalController {

    private final MemberUtil memberUtil;
    @GetMapping("/me")
    public ResponseEntity<MemberInfoInternalResponse> getMemberInfo() {
        return ResponseEntity.ok(MemberInfoInternalResponse.from(memberUtil.getCurrentMember()));
    }

}
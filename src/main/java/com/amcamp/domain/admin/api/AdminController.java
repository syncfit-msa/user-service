package com.amcamp.domain.admin.api;

import com.amcamp.domain.admin.application.AdminService;
import com.amcamp.domain.admin.dto.response.MemberInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/members")
    public List<MemberInfoResponse> memberListInfo() {
        return adminService.getMemberList();
    }

    @DeleteMapping("/members/{memberId}")
    public ResponseEntity<Void> memberDelete(@PathVariable Long memberId) {
        adminService.deleteMember(memberId);
        return ResponseEntity.ok().build();
    }
}

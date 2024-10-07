package com.zerobase.fastlms.admin.controller;

import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.entity.MemberParam;
import com.zerobase.fastlms.admin.model.MemberInput;
import com.zerobase.fastlms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class AdminMemberController {

    private final MemberService memberService;

    //회원관리
    @GetMapping("/admin/member/list.do")
    public String list(Model model,
                       @RequestParam(name = "searchType", required = false) String searchType,
                       @RequestParam(name = "searchValue", required = false) String searchValue,
                       @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<MemberDto> members = memberService.list(searchType, searchValue, pageable);
        model.addAttribute("members", members);
        model.addAttribute("searchType", searchType);
        model.addAttribute("searchValue", searchValue);

        return "admin/member/list";
    }

    //상세정보
    @GetMapping("/admin/member/detail.do")
    public String detail(Model model, MemberParam parameter) {

        parameter.init();
        MemberDto member = memberService.detail(parameter.getUserId());
        model.addAttribute("member", member);


        return "admin/member/detail";
    }

    //회원상태(관리자전용)
    @PostMapping("/admin/member/status.do")
    public String status(Model model, MemberInput parameter) {
        boolean result = memberService.updateStatus(
                parameter.getUserId(), parameter.getUserStatus());
        return "redirect:/admin/member/detail.do?userId=" + parameter.getUserId();
    }

    // 비밀번호 초기화(관리자전용)
    @PostMapping("/admin/member/password.do")
    public String password(Model model, MemberInput parameter) {
        boolean result = memberService.updatePassword(
                parameter.getUserId(), parameter.getPassword());
        return "redirect:/admin/member/detail.do?userId=" + parameter.getUserId();
    }
}
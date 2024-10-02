package com.zerobase.fastlms.member.controller;

import com.zerobase.fastlms.member.dto.MemberInput;
import com.zerobase.fastlms.member.dto.ResetPasswordInput;
import com.zerobase.fastlms.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
public class MemberController {
    private final MemberService memberService;

    //로그인
    @RequestMapping("/member/login")
    public String login(Model model, HttpSession session) {
        // 세션에서 에러 메시지 가져오기
        String errorMessage = (String) session.getAttribute("errorMessage");
        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
            // 에러 메시지를 표시한 후 세션에서 제거
            session.removeAttribute("errorMessage");
        }
        return "member/login";
    }

    //비밀번호 찾기
    @GetMapping("/member/find-password")
    public String findPassword(){

        return "member/find_password";
    }

    @PostMapping("/member/find-password")
    public String findPasswordSubmit(
            Model model,
            ResetPasswordInput parameter){
        boolean result =false;
        try{
            result =  memberService.sendResetPassword(parameter);
        }catch (Exception e){
        }
       model.addAttribute("result",result );

       return "member/find_password_result";
    }

    //회원가입
    @GetMapping("/member/register")
    public String register(){

        return "member/register";
    }

    @PostMapping("/member/register")
    public String registerSubmit(Model model, HttpServletRequest request
    , MemberInput parameter){

        boolean result =  memberService.register(parameter);
        model.addAttribute("result" , result);

        return "member/register_complete";
    }
    @GetMapping("/member/email-auth")
    public String emailAuth(Model model ,HttpServletRequest request){
        String uuid = request.getParameter("id");
        System.out.println(uuid);

        boolean result = memberService.emailAuth(uuid);
        model.addAttribute("result",result);
        return "member/email_auth";
    }

    //회원정보
    @GetMapping("/member/info")
    public String memberInfo(){
        return "member/info";
    }

    @GetMapping("/member/reset/password")
    public String resetPassword(Model model ,HttpServletRequest request){
        String uuid = request.getParameter("uuid");

        boolean result = memberService.chcekResetPassword(uuid);
        model.addAttribute("result",result);

        return "member/reset_Password";
    }

    @PostMapping("/member/reset/password")
    public String resetPasswordSubmit(Model model, ResetPasswordInput parameter) {
        boolean result = false;

        try {
            result = memberService.resetPassword(parameter.getUuid(),parameter.getPassword());
        }catch (Exception e){

        }
        model.addAttribute("result",result);
        return "member/reset_password_result";
    }

}

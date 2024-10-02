package com.zerobase.fastlms.member.controller;

import com.zerobase.fastlms.components.MailComponents;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final MailComponents mailComponents;

//    @RequestMapping("/")
//    public String index(){
//        String email = "ghdgptn12@naver.com";
//        String subject = "안녕하세요 제로베이스입니다";
//        String text = "<p>안녕하세요.</P> <p>반갑습니다</p>";
//        mailComponents.sendMail(email , subject , text);
//        return "Index";
//
//    }


}

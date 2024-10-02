package com.zerobase.fastlms.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;

public class UserAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    // 로그인 에러 발생 시
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMessage = "로그인에 실패하였습니다.";

        if (exception instanceof InternalAuthenticationServiceException) {
            errorMessage = exception.getMessage();
        }

        setDefaultFailureUrl("/member/login?error=true");

        // 세션에 에러 메시지 저장
        HttpSession session = request.getSession();
        session.setAttribute("errorMessage", errorMessage);

        System.out.println("로그인에 실패하였습니다.");
        super.onAuthenticationFailure(request, response, exception);
    }

}

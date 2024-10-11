package com.zerobase.fastlms.security;

import com.zerobase.fastlms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    private final MemberService memberService;
    private final CustomAccessDeniedHandler accessDeniedHandler;  // CustomAccessDeniedHandler 주입

    // 비밀번호 인코더를 빈으로 등록
    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 인증 실패 시 사용할 핸들러를 빈으로 등록
    @Bean
    UserAuthenticationFailureHandler getFailureHandler() {
        return new UserAuthenticationFailureHandler();
    }

    // SecurityFilterChain 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable(); // CSRF 비활성화
        http.headers().frameOptions().sameOrigin();
        http
                .authorizeHttpRequests() // 요청 URL에 대한 권한 설정
                .requestMatchers(
                        "/",
                        "/member/register",
                        "/member/email-auth",
                        "/member/find-password",
                        "/member/reset/password"
                ).permitAll() // 위 경로는 모두 허용
                .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN") // 관리자 경로는 ROLE_ADMIN 권한 필요
                .anyRequest().authenticated() // 그 외 모든 요청은 인증 필요
                .and()
                .formLogin() // 로그인 설정
                .loginPage("/member/login") // 로그인 페이지 설정
                .failureHandler(getFailureHandler()) // 인증 실패 시 처리할 핸들러
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler);

        http.logout() // 로그아웃 설정
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout")) // 로그아웃 URL
                .logoutSuccessUrl("/") // 로그아웃 성공 시 이동할 URL
                .invalidateHttpSession(true); // 세션 무효화

        // 사용자 인증 서비스 및 패스워드 인코더 설정
        http.authenticationManager(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)));

        return http.build(); // SecurityFilterChain 빌드
    }

    // AuthenticationManager 빈 설정
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}

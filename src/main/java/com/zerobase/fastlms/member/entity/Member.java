package com.zerobase.fastlms.member.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Member implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String name;
    private String password;
    private String phone;
    private LocalDateTime regDt;  //회원가입 날짜

    private boolean emailAuthYn; //메일 인증 했는지
    private LocalDateTime emailAuthDt; //이메일 인증 날짜
    private  String emailAuthKey; // 회원가입할때 생성해서 메일인증할때 쓰는 Key

    private String resetPasswordKey; // 비밀번호 초기화할때 사용자인증
    private LocalDateTime resetPasswordLimitDt; // 초기화 후 유효 날짜가 안 지나면 또 초기화x

    private boolean adminYn; //관리자 판단


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return userId;
    }

}

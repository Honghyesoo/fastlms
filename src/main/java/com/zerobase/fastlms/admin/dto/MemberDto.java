package com.zerobase.fastlms.admin.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberDto {
    String userId;
    String name;
    String password;
    String phone;
    LocalDateTime regDt;  //회원가입 날짜

    boolean emailAuthYn; //메일 인증 했는지
    LocalDateTime emailAuthDt; //이메일 인증 날짜
    String emailAuthKey; // 회원가입할때 생성해서 메일인증할때

    String resetPasswordKey; // 비밀번호 초기화할때 사용자인증
    LocalDateTime resetPasswordLimitDt; // 초기화 후 유효 날짜가 안 지나면 또 초기화x

    boolean adminYn; //관리자 판단

    //추가컬럼
    long totalCount;
    long seq;

}

package com.zerobase.fastlms.admin.dto;

import com.zerobase.fastlms.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    private Long id;

    private String userId;
    private String name;
    private String password;
    private String phone;
    private LocalDateTime regDt;  //회원가입 날짜

    private boolean emailAuthYn; //메일 인증 했는지
    private LocalDateTime emailAuthDt; //이메일 인증 날짜
    private String emailAuthKey; // 회원가입할때 생성해서 메일인증할때

    private String resetPasswordKey; // 비밀번호 초기화할때 사용자인증
    private LocalDateTime resetPasswordLimitDt; // 초기화 후 유효 날짜가 안 지나면 또 초기화x

    private boolean adminYn; //관리자 판단

    private String userStatus;

      //추가컬럼
    private long totalCount;

    public static MemberDto of(Member member){
        return  MemberDto.builder()
                .id(member.getId())
                .userId(member.getUserId())
                .name(member.getName())
                .phone(member.getPhone())
                .regDt(member.getRegDt())

                .emailAuthYn(member.isEmailAuthYn())
                .emailAuthDt(member.getEmailAuthDt())
                .emailAuthKey(member.getEmailAuthKey())

                .resetPasswordKey(member.getResetPasswordKey())
                .resetPasswordLimitDt(member.getResetPasswordLimitDt())

                .adminYn(member.isAdminYn())
                .userStatus(member.getUserStatus())
                .build();
    }

}

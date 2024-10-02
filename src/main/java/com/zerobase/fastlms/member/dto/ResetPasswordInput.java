package com.zerobase.fastlms.member.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ResetPasswordInput {
    private String userId;
    private String name;
    private String password;
    private String uuid; //비밀번호 인증 id
}

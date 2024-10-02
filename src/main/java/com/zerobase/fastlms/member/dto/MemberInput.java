package com.zerobase.fastlms.member.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MemberInput {
    private Long id;
    private String userId;
    private String name;
    private String password;
    private String phone;
}

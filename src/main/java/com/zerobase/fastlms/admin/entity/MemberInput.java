package com.zerobase.fastlms.admin.entity;

import lombok.Data;


@Data
public class MemberInput {
    String userId;
    String userStatus;
    String password;
}
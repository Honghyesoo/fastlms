package com.zerobase.fastlms.member.Repository;

import com.zerobase.fastlms.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    // userId로 회원을 찾는 메서드
    Optional<Member> findByUserId(String userId);

    // emailAuthKey로 회원을 찾는 메서드
    Optional<Member> findByEmailAuthKey(String emailAuthKey);

    // 비밀번호 초기화
    Optional<Member> findByUserIdAndName(String userId, String name);

    //uuid 찾느 메서드
    Optional<Member> findByResetPasswordKey(String resetPasswordKey);


}


package com.zerobase.fastlms.member.service.impl;

import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.mapper.MemberMapper;
import com.zerobase.fastlms.components.MailComponents;
import com.zerobase.fastlms.member.Repository.MemberRepository;
import com.zerobase.fastlms.member.dto.MemberInput;
import com.zerobase.fastlms.member.dto.ResetPasswordInput;
import com.zerobase.fastlms.member.entity.Member;
//import com.zerobase.fastlms.member.exception.MemberNotEmailAuthException;
import com.zerobase.fastlms.member.exception.MemberNotEmailAuthException;
import com.zerobase.fastlms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final MailComponents mailComponents;
    private final MemberMapper memberMapper;
    /**
     * 회원 가입
     */
    @Override
    public boolean register(MemberInput parameter) {
        // userId로 회원 검색
        Optional<Member> optionalMember = memberRepository.findByUserId(parameter.getUserId());
        if (optionalMember.isPresent()) {
            // 현재 userId에 해당하는 데이터 존재할 때
            return false;
        }

        String encPassword = BCrypt.hashpw(parameter.getPassword(),BCrypt.gensalt());
        String uuid = UUID.randomUUID().toString();

        Member member = Member.builder()
                .userId(parameter.getUserId())
                .name(parameter.getName())
                .password(encPassword)
                .phone(parameter.getPhone())
                .regDt(LocalDateTime.now())
                .emailAuthYn(false)
                .emailAuthKey(uuid)
                .build();
        memberRepository.save(member);



        String email = parameter.getUserId();
        String subject = "fastlms 사이트 가입을 축하드립니다.";
        String text = "<p>fastlms 사이트 가입을 축하드립니다.</p> <p>아래 링크를 클릭하셔서 가입 완료 하세요</p>" +
                "<div><a target='_blank' href='http://localhost:8080/member/email-auth?id=" + uuid + "'>가입완료</a></div>";
        mailComponents.sendMail(email,subject,text);
        return true;
    }

    //이메일 인증
    @Override
    public boolean emailAuth(String uuid) {

        Optional<Member> optionalMember = memberRepository.findByEmailAuthKey(uuid);

        if (optionalMember.isEmpty()){ //존재하지 않은경우
            return false;
        }
        Member member = optionalMember.get();

        if (member.isEmailAuthYn()){
            return false;
        }
        member.setEmailAuthYn(true);
        member.setEmailAuthDt(LocalDateTime.now());
        memberRepository.save(member);

        return true;
    }

    // 비밀번로 초기화 로직
    @Override
    public boolean sendResetPassword(ResetPasswordInput parameter) {
        Optional<Member> optionalMember = memberRepository.findByUserIdAndName(
                parameter.getUserId(), parameter.getName());
        if (optionalMember.isEmpty()) {
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }

        Member member = optionalMember.get();
        String uuid  =UUID.randomUUID().toString();

        member.setResetPasswordKey(uuid);
        member.setResetPasswordLimitDt(LocalDateTime.now().plusDays(1));
        memberRepository.save(member);

        String email = parameter.getUserId();
        String subject = "[fastlms] 비밀번호 초기화 메일 입니다.";
        String text = "<p>[fastlms] 비밀번호 초기화 메일 입니다.</p>" +
                " <p>아래 링크를 클릭하셔서 비밀번호 초기화 해주세요</p>" +
                "<div><a target='_blank' href='http://localhost:8080/member/reset/password?uuid="
                + uuid + "'>비밀번호 초기화 링크</a></div>";
        mailComponents.sendMail(email,subject,text);

        return true;
    }

    //비밀번호 초기화
    @Override
    public boolean resetPassword(String uuid, String password) {
        Optional<Member> optionalMember = memberRepository.findByResetPasswordKey(uuid);
        if (optionalMember.isEmpty()) {
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }

        Member member = optionalMember.get();

        //초기화 날짜가 유효한지 체크
        if (member.getResetPasswordLimitDt() == null){
            throw new RuntimeException("유효한 날짜가 아닙니다.");
        }
        //현재 시간보다 이전 값에 있는지?
        if (member.getResetPasswordLimitDt().isBefore(LocalDateTime.now())){
            throw new RuntimeException("유효한 날짜가 아닙니다.");
        }
        String encPassword = BCrypt.hashpw(password,BCrypt.gensalt());
        member.setPassword(encPassword);
        member.setResetPasswordKey("");
        member.setResetPasswordLimitDt(null);
        memberRepository.save(member);

        return true;
    }

    // 입력받은 uuid가 유효한지 확인 <비밀번호 초기화시>
    @Override
    public boolean chcekResetPassword(String uuid) {
        Optional<Member> optionalMember = memberRepository.findByResetPasswordKey(uuid);
        if (optionalMember.isEmpty()) {
            return false;
        }

        Member member = optionalMember.get();

        //초기화 날짜가 유효한지 체크
        if (member.getResetPasswordLimitDt() == null){
            throw new RuntimeException("유효한 날짜가 아닙니다.");
        }

        if (member.getResetPasswordLimitDt().isBefore(LocalDateTime.now())){
            throw new RuntimeException("유효한 날짜가 아닙니다.");
        }
        return true;
    }

    //회원목록
    @Override
    public Page<MemberDto> list(String searchType, String searchValue, Pageable pageable) {
        if (searchType == null || searchValue == null) {
            return memberRepository.findAll(pageable).map(this::convertToDto);
        }

        switch (searchType) {
            case "userId":
                return memberRepository.findByUserIdContaining(searchValue, pageable).map(this::convertToDto);
            case "name":
                return memberRepository.findByNameContaining(searchValue, pageable).map(this::convertToDto);
            case "phone":
                return memberRepository.findByPhoneContaining(searchValue, pageable).map(this::convertToDto);
            default:
                return memberRepository.findByUserIdContainingOrNameContainingOrPhoneContaining(
                        searchValue, searchValue, searchValue, pageable).map(this::convertToDto);
        }
    }

    private MemberDto convertToDto(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .userId(member.getUserId())
                .name(member.getName())
                .phone(member.getPhone())
                .emailAuthYn(member.isEmailAuthYn())
                .adminYn(member.isAdminYn())
                .regDt(member.getRegDt())
                .build();
    }

    //회원 상세정보
    @Override
    public MemberDto detail(String userId) {
        Optional<Member> optionalMember= memberRepository.findByUserId(userId);
        System.out.println(optionalMember);

        if (optionalMember.isEmpty()){
            return null;
        }
        Member member = optionalMember.get();

        return MemberDto.of(member);
    }

    //로그인
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<Member> optionalMember = memberRepository.findByUserId(userName);
        if (optionalMember.isEmpty()) {
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }

        Member member = optionalMember.get();
        if (!member.isEmailAuthYn()){
            throw  new MemberNotEmailAuthException("이메일 활성화 이후에 로그인을 해주세요.");
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        //관리자일때
        if (member.isAdminYn()){
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        return new User(member.getUserId(), member.getPassword(), grantedAuthorities);
    }

}

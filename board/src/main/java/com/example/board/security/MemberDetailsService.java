package com.example.board.security;

import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.board.entity.Member;
import com.example.board.entity.MemberRole;
import com.example.board.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    // 회원가입

    public void register(MemberDTO dto) throws IllegalStateException {
        // dto=> entity
        // 비밀번호 암호화
        // 중복확인
        validateEmail(dto.getEmail());

        Member member = Member.builder()
                .email(dto.getEmail())
                .fromSocial(dto.isFromSocial())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .build();
        member.addMemberRole(MemberRole.USER);
        memberRepository.save(member);

    }

    // 이메일 중복여부확인
    private void validateEmail(String email) {
        Optional<Member> member = memberRepository.findById(email);

        // IllegalStateException : RuntimeException(실행해야 나오는 예외)
        if (member.isPresent()) {
            throw new IllegalStateException("이미 사용중인 이메일 입니다");
        }
    }

    @Override // 로그인을 처리해주는 서비스 메소드
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("username{}", username);

        Member member = memberRepository.findByEmailAndFromSocial(username, false);

        if (member == null) {
            throw new UsernameNotFoundException("이메일 확인");
        }
        // entity => dto
        AuthMemberDTO authMemberDTO = new AuthMemberDTO(member.getEmail(), member.getPassword(),
                member.isFromSocial(),
                member.getRoleSet().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                        .collect(Collectors.toList()));

        authMemberDTO.setName(member.getName());
        authMemberDTO.setFromSocial(member.isFromSocial());

        return authMemberDTO;
    }

}
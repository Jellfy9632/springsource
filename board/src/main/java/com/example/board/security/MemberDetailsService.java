package com.example.board.security;

import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.board.entity.Member;
import com.example.board.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

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
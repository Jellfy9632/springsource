package com.example.jpa.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import com.example.jpa.entity.Member;
import com.example.jpa.entity.Member.RoleType;
import com.example.jpa.entity.QMember;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void memberInsertTest() {
        IntStream.rangeClosed(1, 20).forEach(i -> {
            Member member = Member.builder()
                    .name("홍길동" + i)
                    .roleType(RoleType.USER)
                    .age(i * 5)
                    .description("user" + i)
                    .build();
            memberRepository.save(member);
        });
    }

    @Test
    public void queryDslTest() {
        QMember member = QMember.member;
        // where name = '홍길동3'
        memberRepository.findAll(member.name.eq("홍길동3"));

        // where age > 15
        memberRepository.findAll(member.age.gt(15L));

        // where roleType =USER
        memberRepository.findAll(member.roleType.eq(RoleType.USER));

        // where name like ' %길동'
        memberRepository.findAll(member.name.contains("길동"));
        // 전체 조회 후 no의 내림차순으로 정렬하여 출력
        System.out.println(memberRepository.findAll(Sort.by("no").descending()));
    }
}

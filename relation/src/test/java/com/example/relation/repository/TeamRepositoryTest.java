package com.example.relation.repository;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.relation.entity.team.Team;
import com.example.relation.entity.team.TeamMember;
import com.example.relation.repository.team.TeamMemberRepository;
import com.example.relation.repository.team.TeamRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
public class TeamRepositoryTest {

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Test
    public void findByMemberEqualTeamTest() {
        List<Object[]> result = teamMemberRepository.findByMemberEqualTeam(2L);
        for (Object[] objects : result) {
            System.out.println(Arrays.toString(objects));
        }

    }

    @Test
    public void readTest3() {
        Team tema = Team.builder().id(2L).build();
        List<TeamMember> list = teamMemberRepository.findByTeam(tema);
        System.out.println(list);

    }

    @Test
    public void insetTest() {
        // 팀 정보 삽입

        Team team = teamRepository.save(Team.builder().teamName("team2").build());

        // 회원정보 삽입
        teamMemberRepository.save(TeamMember.builder().username("user1").team(team).build());
    }

    @Test
    public void insetTest2() {
        // 팀 정보 삽입

        Team team = teamRepository.findById(1L).get();

        // 회원정보 삽입
        teamMemberRepository.save(TeamMember.builder().username("user2").team(team).build());
    }

    @Test
    public void readTest() {
        // 팀 조회
        Team team = teamRepository.findById(1L).get();
        // 멤버 조회
        TeamMember member1 = teamMemberRepository.findById(1L).get();

        System.out.println(team);
        System.out.println(member1);
    }

    @Test
    public void readTest2() {

        // 해당하는 멤버의 팀 정보 조회
        TeamMember member1 = teamMemberRepository.findById(1L).get();
        System.out.println(member1);
        // 객체그래프 탐색
        System.out.println(member1.getTeam());
    }

    @Test
    public void updateTest() {
        // 1번 팀원의 팀 변경: 2번팀으로 변경
        TeamMember member = teamMemberRepository.findById(1L).get();
        Team team = teamRepository.findById(2L).get();

        member.setTeam(team);

        teamMemberRepository.save(member);

    }

    @Test
    public void deleteTest() {
        // 1번 팀 삭제
        // 무결성 제약조건(C##JAVA.FK9UBP79EI4TV4CRD0R9N7U5I6E)이 위배되었습니다- 자식 레코드가 발견되었습니다

        // 해결
        // 1. 삭제하려고 하는 팀의 팀원들을 다른 팀으로 이동
        // 2. 자식레코드를 삭제한 후 부모를 삭제

        TeamMember member = teamMemberRepository.findById(2L).get();
        Team team = teamRepository.findById(2L).get();
        member.setTeam(team);
        teamMemberRepository.save(member);

        teamRepository.deleteById(1L);
    }

    // -------------------------------
    // 양방향 연관관계 : @OneToMany
    // ㄴ 단방향 2개
    // -------------------------------

    // 팀 -> 회원
    // @Transactional
    @Test
    public void readBiTest1() {

        // 해당하는 멤버의 팀 정보 조회
        // 팀찾기
        Team team = teamRepository.findById(2L).get();
        System.out.println(team);

        team.getMembers().forEach(member -> System.out.println(member));
    }

    // -------------------------------
    // 양방향 연관관계에서
    // 영속성 전이: Cascade
    // -------------------------------

    @Test
    public void insetTest3() {

        Team team = Team.builder().teamName("team3").build();

        TeamMember member = TeamMember.builder().username("홍길동").team(team).build();
        team.getMembers().add(member);
        teamRepository.save(team);
    }

    @Test
    public void deleteTest2() {
        // 부모 삭제시 자식도 같이 삭제
        // deleteTest()와 비교
        teamRepository.deleteById(3L);
    }

}

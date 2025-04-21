package com.example.relation.entity.team;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString(exclude = "members")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Team {
    // 하나의 팀에는 여러 회원이 소속된다
    // id, name(팀명)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;

    private String teamName;

    @Builder.Default
    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER) // 관계있는 테이블 정보를 즉시 가지고 나오기
    // : One 테이블만 select 실행
    // => 해결법 1) @Transactional
    // 2) FetchType.EAGER =>left join 처리
    private List<TeamMember> members = new ArrayList<>();

}

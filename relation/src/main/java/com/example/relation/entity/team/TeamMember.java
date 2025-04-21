package com.example.relation.entity.team;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString(exclude = "team")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamMember {
    // 회원은 단 하나의 팀에 소속된다
    // id, name(회원명)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String username;

    // 외래키 필드명 설정
    // @JoinColumn 미 사용시 기본형태는 (table명_pk명)
    @JoinColumn(name = "team_id")
    // 엔티티간 관계 설정
    @ManyToOne // : left join 이 기본
    private Team team;
}

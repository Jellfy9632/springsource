package com.example.board.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.board.entity.Board;
import com.example.board.entity.Member;
import com.example.board.entity.Reply;

public interface MemberRepository extends JpaRepository<Member, String> {

    @EntityGraph(attributePaths = { "roleSet" }, type = EntityGraphType.LOAD)
    Member findByEmailAndFromSocial(String email, boolean fromSocial);
}

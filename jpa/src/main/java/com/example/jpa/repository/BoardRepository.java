package com.example.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;

import com.example.jpa.entity.Board;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    // // WHERE b.WRITER = 'user4'
    // List<Board> findByWriter(String writer);

    // List<Board> findByTitle(String title);

    // List<Board> findByWriterStartingWith(String writer);

    // List<Board> findByWriterEndingWith(String writer);

    // List<Board> findByWriterContaining(String writer);

    // List<Board> findByWriterContainingOrContentContaining(String writer, String
    // content);

    // // b.WRITER like %user or b. content like '%내용%'
    // List<Board> findByWriterContainingAndContentContaining(String writer, String
    // content);

    // // bno > 5 게시물 조회
    // List<Board> findByBnoGreaterThan(Long Bno);

    // List<Board> findByBnoGreaterThanOrderByBnoDesc(Long bno);

    // // bno >= 5 and bno <= 10

    // List<Board> findByBnoBetween(Long start, Long end);

    // --------------------------------------------------

    // @Query

    // --------------------------------------------------
    @Query("select b from Board b where b.writer = ?1")
    List<Board> findByWriter(String writer);

    @Query("select b from Board b where b.writer like ?1%")
    List<Board> findByWriterStartingWith(String writer);

    @Query("select b from Board b where b.writer like %?1%")
    List<Board> findByWriterContaining(String writer);

    // nativeQuery = true : sql 구문 형식 사용법
    // from 이후 DB 테이블 명을 직접 사용

    // 1) @Query(value = "select * from Board b where b.bno < ?1", nativeQuery
    // =true)
    // 2)
    @NativeQuery("select * from Board b where b.bno < ?1")
    List<Board> findByBnoGreaterThan(Long Bno);
}

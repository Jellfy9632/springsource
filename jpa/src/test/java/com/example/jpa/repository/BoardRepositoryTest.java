package com.example.jpa.repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.jpa.entity.Board;
import com.example.jpa.entity.Memo;
import com.example.jpa.entity.QBoard;

@SpringBootTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void queryDslTest() {

        // Q 파일 사용
        QBoard board = QBoard.board;

        // where b. title = 'Board1'
        // System.out.println(boardRepository.findAll(board.title.eq("Board1")));

        // where b. title like 'Board1%'
        // System.out.println(boardRepository.findAll(board.title.startsWith("Board1")));

        // where b. title like '%Board1'
        // System.out.println(boardRepository.findAll(board.title.endsWith("Board1")));

        // where b. title like '%Board1%'
        // System.out.println(boardRepository.findAll(board.title.contains("Board1")));

        // where b. title like '%Board1%' and b.bno > 0 order by bno desc
        // Iterable<Board> boards =
        // boardRepository.findAll(board.title.contains("Board1")
        // .and(board.bno.gt(0L)), Sort.by("bno").descending());
        // System.out.println(boards);

        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        Page<Board> result = boardRepository.findAll(board.bno.gt(0L), pageable);

        System.out.println("page size : " + result.getSize());
        System.out.println("page TotalPage :" + result.getTotalPages());
        System.out.println("page TotalElements : " + result.getTotalElements());
        System.out.println("page content : " + result.getContent());
    }

    @Test
    public void queryMethodTest() {
        // System.out.println(boardRepository.findByWriter("홍길동2"));
        // System.out.println(boardRepository.findByTitle("Board"));
        // System.out.println(boardRepository.findByWriterStartingWith("홍")); // user%
        // System.out.println(boardRepository.findByWriterEndingWith("홍")); // %홍길동
        // System.out.println(boardRepository.findByWriterContaining("홍")); // %%홍길동
        // System.out.println(boardRepository.findByWriterContainingOrContentContaining("5",
        // "9"));
        // System.out.println(boardRepository.findByWriterContainingAndContentContaining("5",
        // "9"));

        // System.out.println(boardRepository.findByBnoGreaterThan(5L));
        // System.out.println(boardRepository.findByBnoGreaterThanOrderByBnoDesc(5L));
        // System.out.println(boardRepository.findByBnoBetween(5L, 10L));

        List<Object[]> result = boardRepository.findByTitle2("Board");
        for (Object[] objects : result) {
            System.out.println(Arrays.toString(objects));
            String title = (String) objects[0];
            String writer = (String) objects[1];
            System.out.println("title : " + title + "wirter : " + writer);
            System.out.println("=============================");
        }
    }

    // C : insert
    @Test
    public void insertTest() {
        LongStream.rangeClosed(1, 100).forEach(i -> {
            Board board = Board.builder().title("Board" + i).writer("홍길동" + i).content("" + i).build();

            boardRepository.save(board);
        });

    }

    // C : update
    @Test
    public void updateTest() {

        Board board = boardRepository.findById(10L).get();
        board.setContent("변경된 Content");

        boardRepository.save(board);
    }

    @Test
    public void readTest() {
        Board board = boardRepository.findById(10L).get();
        System.out.println(board);
    }

    @Test
    public void listTest() {
        // boardRepository.findAll().forEach(board -> System.out.println(board));
        // page 자동 구분
        // pageNumber : 0 => 1 page
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        boardRepository.findAll(pageable).forEach(board -> System.out.println(board));
    }

    @Test
    public void deleteTest() {
        boardRepository.deleteById(10L);
    }
}

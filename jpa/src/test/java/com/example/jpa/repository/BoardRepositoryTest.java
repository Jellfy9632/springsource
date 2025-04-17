package com.example.jpa.repository;

import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Board;
import com.example.jpa.entity.Memo;

@SpringBootTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    // C : insert
    @Test
    public void insertTest() {
        LongStream.rangeClosed(1, 10).forEach(i -> {
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
        boardRepository.findAll().forEach(board -> System.out.println(board));
    }

    @Test
    public void deleteTest() {
        boardRepository.deleteById(10L);
    }
}

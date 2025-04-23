package com.example.todo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.todo.entity.ToDo;

@SpringBootTest
public class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @Test
    public void testInsert() {

        IntStream.rangeClosed(1, 10).forEach(i -> {
            ToDo todo = ToDo.builder()
                    .content("강아지산책" + i)
                    .completed(false)
                    .importanted(false)
                    .build();
            todoRepository.save(todo);
        });
    }

    @Test
    public void testTodoRead() {
        todoRepository.findAll().forEach(todo -> System.out.println(todo));

    }

    // todo 삭제
    @Test
    public void testTodoDelete() {
        todoRepository.deleteById(1L);
    }

    // todo 수정 - 완료
    @Test
    public void testTodoUpdate() {
        ToDo todo = todoRepository.findById(2L).get();
        todo.setCompleted(true);
        todoRepository.save(todo);
    }

    @Test
    public void testTodoRead2() {
        // Todo 완료목록 추출
        todoRepository.findByCompleted(true).forEach(todo -> System.out.println(todo));

    }

    @Test
    public void testTodoRead3() {
        // 안중요
        todoRepository.findByImportanted(false).forEach(todo -> System.out.println(todo));

    }
}

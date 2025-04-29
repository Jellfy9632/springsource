package com.example.book.repository;

import static org.mockito.ArgumentMatchers.isNull;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.book.entity.Book;

import groovyjarjarantlr4.v4.runtime.misc.IntSet;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testInsert() {
        IntStream.rangeClosed(1, 20).forEach(i -> {

            Book book = Book.builder()
                    .title("book title" + i)
                    .author("author" + i)
                    .price(1000 * i)
                    .build();
            bookRepository.save(book);
        });
        // 20권
    }

    @Test
    public void testList() {

        bookRepository.findAll().forEach(book -> System.out.println(book));
    }

    @Test
    public void testList2() {

        Pageable pageable = PageRequest.of(1, 10, Sort.by("code").descending());

        Page<Book> result = bookRepository.findAll(pageable);
        result.getContent().forEach(book -> System.out.println(book));
        System.out.println("전체 행 개수 " + result.getTotalElements());
        System.out.println("전체 페이지 수 " + result.getTotalPages());

    }

    @Test
    public void testGet() {
        System.out.println(bookRepository.findById(2L));

    }

    @Test
    public void testUpdate() {
        Book book = bookRepository.findById(2L).get();
        book.setPrice(20000);
        bookRepository.save(book);

    }

    @Test
    public void testRemove() {
        bookRepository.deleteById(4L);
    }
}

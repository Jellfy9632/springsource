package com.example.book.repository;

import static org.mockito.ArgumentMatchers.isNull;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

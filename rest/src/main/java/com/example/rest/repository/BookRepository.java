package com.example.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.example.rest.entity.Book;
import com.example.rest.entity.QBook;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public interface BookRepository extends JpaRepository<Book, Long>, QuerydslPredicateExecutor<Book> {

    // 임의의 값을 포함하는 결과를 출력
    // 방법 1) findBy
    // List<Book> findByAuthorContains(String author);
    // List<Book> findByTitleContains(String title);

    // 방법 2) @Query

    // 방법 3) Querydsl

    public default Predicate makePredicate(String type, String keyword) {

        System.out.println("type " + type + " keyword " + keyword);

        QBook book = QBook.book;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(book.code.gt(0));

        if (type == null) {
            return builder;
        }

        if (type.equals("t")) {
            builder.and(book.title.contains(keyword));
        } else {
            builder.and(book.author.contains(keyword));
        }
        return builder;
    }

}

package com.example.todo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Table(name = "todo")
@Entity
@Builder
public class ToDo {
    // -내용 , 작성일, 수정일자, 완료여부 (true,false) , 중요도 (true,false)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TODO_ID")
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(columnDefinition = "NUMBER(1) DEFAULT 0")
    private boolean completed;

    @Column(columnDefinition = "NUMBER(1) DEFAULT 0")
    private boolean importanted;

}

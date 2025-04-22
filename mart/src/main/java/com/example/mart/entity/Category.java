package com.example.mart.entity;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Category extends BaseEntity {

    @Id
    @Column(name = "CATEGORY_ID")

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "Category")
    private CategoryItem categoryItem;

}

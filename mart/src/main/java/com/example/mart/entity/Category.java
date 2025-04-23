package com.example.mart.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@ToString(exclude = "categoryItem")
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

    @Builder.Default
    @OneToMany(mappedBy = "category")
    private List<CategoryItem> categoryItem = new ArrayList<>();

    // @ManyToMany를 사용한다면
    @Builder.Default
    @JoinTable(name = "CATEGORY_ITEM1", joinColumns = @JoinColumn(name = "CATEGORY_ID"), inverseJoinColumns = @JoinColumn(name = "ITEM_ID"))
    @ManyToMany
    private List<Item> items = new ArrayList<>();

}

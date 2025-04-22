package com.example.mart.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString(exclude = { "item", "order" })
@Entity
public class OrderItem {
    // 주문상품에 대한 주문번호, 상품번호, 주문가격, 수량 정보

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ITEM_ID")
    private Long id; // 주문번호 역할

    // 하나의 상품은 여러 주문 상품으로 들어 갈 수 있음
    @JoinColumn(name = "ITEM_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    // 하나의 주문에는 주문상품이 여러 개 포함
    @JoinColumn(name = "ORDER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    private int orderPrice; // 주문가격
    private int count; // 주문 수량
}

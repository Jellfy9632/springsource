package com.example.mart.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.LastModifiedDate;

import com.example.mart.entity.constant.OrderStatus;

import jakarta.annotation.Generated;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = { "member", "orderItems", "delivery" })
@Table(name = "ORDERS") // order 테이블명 사용할 수 없음 : order by 때문에
@Entity
public class Order {
    // 주문번호,주문회원,주문시간,주문상태, 정보(ORDER, CANCLE)를 유지

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OREDER_ID")
    private Long id;

    @JoinColumn(name = "MEMBER_ID") // FK명명
    @ManyToOne
    private Member member;

    @LastModifiedDate
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;

    }

    @Builder.Default
    @OneToMany(mappedBy = "order", cascade = { CascadeType.REMOVE, CascadeType.PERSIST }, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @JoinColumn(name = "DELIVERY_ID", unique = true)
    @OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
    private Delivery delivery;

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }
}

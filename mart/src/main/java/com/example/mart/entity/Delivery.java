package com.example.mart.entity;

import com.example.mart.entity.constant.DeliveryStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "order")
@Entity
@Getter
public class Delivery {
    // 하나의 주문은 하나의 배송과 연결된다.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DELIVERY_ID")
    private Long id;

    @Column(nullable = false)
    private String zipcode;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String street;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

}

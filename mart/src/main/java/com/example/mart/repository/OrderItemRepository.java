package com.example.mart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mart.entity.Order;
import com.example.mart.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    void save(Order order);

}

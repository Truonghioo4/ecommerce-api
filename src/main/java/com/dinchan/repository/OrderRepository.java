package com.dinchan.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dinchan.model.Order;
public interface OrderRepository extends JpaRepository<Order, Long>  {
  List<Order> findByUserId(Long userId);
  List<Order> findBySellerId(Long sellerId);
}

package com.dinchan.service;

import java.util.List;
import java.util.Set;

import com.dinchan.domain.OrderStatus;
import com.dinchan.model.*;

public interface OrderService {
  Set<Order>  createOrder(User user, Address shippingAdress, Cart cart);
  Order findOrderById(Long id) throws Exception;
  List<Order> usersOrderHistory(Long userId);
  List<Order> sellersOrder(Long sellerId);
  Order updateOrderStatus(Long orderId, OrderStatus orderStatus) throws Exception;
  Order cancelOrder(Long orderId, User user) throws Exception;
  OrderItem getOrderItemById(Long id) throws Exception;
}

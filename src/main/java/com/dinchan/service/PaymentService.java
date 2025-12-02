package com.dinchan.service;

import com.dinchan.model.Order;
import com.dinchan.model.PaymentOrder;
import com.dinchan.model.User;

import java.util.Set;

public interface PaymentService {
  PaymentOrder createOrder(User user, Set<Order> orders);
  PaymentOrder getPaymentOrderById(Long orderId) throws Exception;
  PaymentOrder getPaymentOrderByPaymentId(String paymentId) throws Exception;

}

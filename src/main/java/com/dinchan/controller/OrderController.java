package com.dinchan.controller;

import com.dinchan.domain.PaymentMethod;
import com.dinchan.model.*;
import com.dinchan.service.CartService;
import com.dinchan.service.OrderService;
import com.dinchan.service.SellerService;
import com.dinchan.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
  private final OrderService orderService;
  private final UserService userService;
  private final CartService cartService;

  private final SellerService sellerService;

  @PostMapping()
  public ResponseEntity<Set<Order>> createOrder(
      @RequestBody Address shippingAddress,
      @RequestParam PaymentMethod paymentMethod,
      @RequestHeader("Authorization")String jwt)
      throws Exception {
    User user = userService.findUserByJwtToken(jwt);
    Cart cart = cartService.findUserCart(user);
    Set<Order> orders = orderService.createOrder(user, shippingAddress, cart);
    return new ResponseEntity<>(orders, HttpStatus.OK);
  }

  @GetMapping("/user")
  public ResponseEntity<List<Order>> userOrderHistory(
      @RequestHeader("Authorization")String jwt) throws Exception {
    User user = userService.findUserByJwtToken(jwt);
    List<Order> orders = orderService.usersOrderHistory(user.getId());
    return new ResponseEntity<>(orders, HttpStatus.ACCEPTED);
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<Order> getOrderById(
      @PathVariable("orderId") Long orderId,
      @RequestHeader("Authorization") String jwt) throws Exception {
    User user = userService.findUserByJwtToken(jwt);
    Order order = orderService.findOrderById(orderId);
    return new ResponseEntity<>(order, HttpStatus.ACCEPTED);
  }

  @GetMapping("/item/{orderItemId}")
  public ResponseEntity<OrderItem> getOrderItemById(
      @PathVariable("orderItemId") Long orderItemId,
      @RequestHeader("Authorization") String jwt) throws Exception {
    User user = userService.findUserByJwtToken(jwt);
    OrderItem orderItem = orderService.getOrderItemById(orderItemId);
    return new ResponseEntity<>(orderItem, HttpStatus.ACCEPTED);
  }

  @PutMapping("/{orderId}/cancel")
  public ResponseEntity<Order> cancelOrder(
      @PathVariable("orderId") Long orderId,
      @RequestHeader("Authorization")String jwt) throws Exception {
    User user = userService.findUserByJwtToken(jwt);
    Order order = orderService.cancelOrder(orderId, user);

    Seller seller = sellerService.getSellerById(order.getSellerId());
    return ResponseEntity.ok(order);
  }
}

package com.dinchan.controller;

import com.dinchan.domain.OrderStatus;
import com.dinchan.exceptions.SellerException;
import com.dinchan.model.Order;
import com.dinchan.model.Seller;
import com.dinchan.service.OrderService;
import com.dinchan.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seller/orders")
public class SellerOrderController {
  private final OrderService orderService;
  private final SellerService sellerService;

  @GetMapping()
  public ResponseEntity<List<Order>> getAllOrders(
      @RequestHeader("Authorization") String jwt) throws Exception {
    Seller seller = sellerService.getSellerProfile(jwt);
    List<Order> orders = orderService.sellersOrder(seller.getId());
    return new ResponseEntity<>(orders, HttpStatus.OK);
  }

  @PatchMapping("/{orderId}/status/{orderStatus}")
  public ResponseEntity<Order> updateOrderStatus(
      @RequestHeader("Authorization") String jwt,
      @PathVariable("orderId")Long orderId,
      @PathVariable("orderStatus") OrderStatus orderStatus
  ) throws Exception {
    Order order = orderService.updateOrderStatus(orderId, orderStatus);
    return new ResponseEntity<>(order, HttpStatus.ACCEPTED);
  }
}

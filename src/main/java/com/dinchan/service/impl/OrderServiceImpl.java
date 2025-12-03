package com.dinchan.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.dinchan.domain.PaymentStatus;
import com.dinchan.model.*;
import com.dinchan.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

import com.dinchan.domain.OrderStatus;
import com.dinchan.repository.AddressRepository;
import com.dinchan.repository.OrderRepository;
import com.dinchan.service.OrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;
  private final AddressRepository addressRepository;
  private final OrderItemRepository orderItemRepository;

  @Override
  public Set<Order> createOrder(User user, Address shippingAdress, Cart cart) {

    if (!user.getAddresses().contains(shippingAdress)) {
      user.getAddresses().add(shippingAdress);
    }

    Address address = addressRepository.save(shippingAdress);

    // Group products that user chose by seller shop
    Map<Long, List<CartItem>> itemsBySeller = cart.getCartItems().stream()
        .collect(Collectors.groupingBy(item -> item.getProduct()
            .getSeller().getId()));

    Set<Order> orders = new HashSet<>();

    //Iterate through the items in the hashmap and create an order for each item based on the sellerId
    for (Map.Entry<Long, List<CartItem>> entry : itemsBySeller.entrySet()) {
      Long sellerId = entry.getKey();
      List<CartItem> items = entry.getValue();

      int toalOrderPrice = items.stream().mapToInt(
          CartItem::getSellingPrice
      ).sum();
      int totalItem = items.stream().mapToInt(CartItem::getQuantity).sum();
      Order createdOrder = new Order();
      createdOrder.setUser(user);
      createdOrder.setSellerId(sellerId);
      createdOrder.setTotalMrpPrice(toalOrderPrice);
      createdOrder.setTotalSellingPrice(toalOrderPrice);
      createdOrder.setTotalItem(totalItem);
      createdOrder.setShippingAddress(address);
      createdOrder.setOrderStatus(OrderStatus.PENDING);

      Order savedOrder = orderRepository.save(createdOrder);
      orders.add(savedOrder);

      List<OrderItem> orderItems = new ArrayList<>();
      for (CartItem item : items) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(savedOrder);
        orderItem.setMrpPrice(item.getMrpPrice());
        orderItem.setProduct(item.getProduct());
        orderItem.setQuantity(item.getQuantity());
        orderItem.setSize(item.getSize());
        orderItem.setUserId(item.getUserId());
        orderItem.setSellingPrice(item.getSellingPrice());
        savedOrder.getOrderItems().add(orderItem);

        OrderItem savedOrderItem = orderItemRepository.save(orderItem);
        orderItems.add(savedOrderItem);
      }
    }
    return orders;
  }

  @Override
  public Order findOrderById (Long id) throws Exception {
    return orderRepository.findById(id).orElseThrow(()->
        new Exception("order not found..."));
  }

  @Override
  public List<Order> usersOrderHistory (Long userId){
    return orderRepository.findByUserId(userId);
  }

  @Override
  public List<Order> sellersOrder (Long sellerId){
    return orderRepository.findBySellerId(sellerId);
  }

  @Override
  public Order updateOrderStatus (Long orderId, OrderStatus orderStatus) throws Exception {
    Order order = findOrderById(orderId);
    order.setOrderStatus(orderStatus);
    return orderRepository.save(order);
  }

  @Override
  public Order cancelOrder (Long orderId, User user) throws Exception {
    Order order = findOrderById(orderId);
    if(!user.getId().equals(order.getUser().getId())) {
      throw new Exception("you don't have access to this order");
    }
    order.setOrderStatus(OrderStatus.CANCELLED);
    return orderRepository.save(order);
  }

  @Override
  public OrderItem getOrderItemById(Long id) throws Exception {
    return orderItemRepository.findById(id).orElseThrow(()->
        new Exception("order item not exist..."));
  }
}

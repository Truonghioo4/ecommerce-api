package com.dinchan.service.impl;

import com.dinchan.config.VNPayConfig;
import com.dinchan.model.Order;
import com.dinchan.model.PaymentOrder;
import com.dinchan.model.User;
import com.dinchan.repository.OrderRepository;
import com.dinchan.repository.PaymentOrderRepository;
import com.dinchan.response.PaymentLinkResponse;
import com.dinchan.service.PaymentService;
import com.dinchan.utils.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
  private final PaymentOrderRepository paymentOrderRepository;
  private final OrderRepository orderRepository;
  private final VNPayConfig vnPayConfig;

  @Override
  public PaymentOrder createOrder(User user, Set<Order> orders) {
    Long amount = orders.stream().mapToLong(Order::getTotalSellingPrice).sum();
    PaymentOrder paymentOrder = new PaymentOrder();
    paymentOrder.setUser(user);
    paymentOrder.setAmount(amount);
    paymentOrder.setOrders(orders);
    paymentOrderRepository.save(paymentOrder);
    return null;
  }

  @Override
  public PaymentOrder getPaymentOrderById(Long orderId) throws Exception {
    return paymentOrderRepository.findById(orderId).orElseThrow(()->
        new Exception("payment order not found..."));
  }

  @Override
  public PaymentOrder getPaymentOrderByPaymentId(String paymentId) throws Exception {
    PaymentOrder paymentOrder = paymentOrderRepository.findByPaymentLinkId(paymentId);
    if (paymentOrder == null) {
      throw new Exception("payment order not found with provided payment link id");
    }
    return null;
  }

  public PaymentLinkResponse createVNPayPaymentLink(HttpServletRequest request) {
    long amount = Integer.parseInt(request.getParameter("s:amount")) * 100L; // VN pay 100L => 100000
    String bankCode = request.getParameter("s:bankCode");

    Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
    vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
    if (bankCode != null && !bankCode.isEmpty()) {
      vnpParamsMap.put("vnp_BankCode", bankCode);
    }

    vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));
    //build query url

    String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
    String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
    String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getVnp_SecretKey(), hashData);
    queryUrl += "&vnp_SecureHash=" + vnpSecureHash;

    String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
    return PaymentLinkResponse.builder()
        .code("ok")
        .message("success")
        .payment_link_url(paymentUrl)
        .build();
  }
}

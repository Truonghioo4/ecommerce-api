package com.dinchan.controller;

import com.dinchan.model.User;
import com.dinchan.response.ApiResponse;
import com.dinchan.response.PaymentLinkResponse;
import com.dinchan.service.PaymentService;
import com.dinchan.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {
  private final PaymentService paymentService;
  private final UserService userService;

//  @GetMapping("/{paymentId}")
//  public ResponseEntity<ApiResponse> paymentSuccessHandler(
//      @PathVariable String paymentId,
//      @RequestParam String paymentLinkId,
//      @RequestHeader("Authorization")String jwt) throws Exception{
//    User user = userService.findUserByJwtToken(jwt);
//
//    PaymentLinkResponse paymentResponse;
//  }
}

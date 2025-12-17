package com.dinchan.request;

import lombok.Data;

@Data
public class CreateNotificationRequest {
  private String type; // ORDER, PRODUCT, SYSTEM
  private String message;
  private Object data; // Optional: Order, Product, hoặc data khác
}


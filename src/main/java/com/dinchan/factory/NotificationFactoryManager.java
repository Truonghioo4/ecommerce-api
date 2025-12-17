package com.dinchan.factory;

import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract Factory Manager - Quản lý các Notification Factory
 * Sử dụng Abstract Factory Pattern để tạo notification theo loại
 */
@Component
public class NotificationFactoryManager {

  private final Map<String, NotificationFactory> factories;

  public NotificationFactoryManager(List<NotificationFactory> factoryList) {
    this.factories = new HashMap<>();
    for (NotificationFactory factory : factoryList) {
      factories.put(factory.getNotificationType(), factory);
    }
  }

  /**
   * Lấy factory phù hợp theo type
   */
  public NotificationFactory getFactory(String type) {
    NotificationFactory factory = factories.get(type.toUpperCase());
    if (factory == null) {
      // Default to SYSTEM notification if type not found
      return factories.get("SYSTEM");
    }
    return factory;
  }
}


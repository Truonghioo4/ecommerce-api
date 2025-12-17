package com.dinchan.factory;

import com.dinchan.model.Notifications;
import com.dinchan.model.Product;
import com.dinchan.model.User;
import org.springframework.stereotype.Component;

/**
 * Concrete Factory - Tạo notification cho Product
 */
@Component
public class ProductNotificationFactory implements NotificationFactory {

  @Override
  public Notifications createNotification(User user, String message, Object data) {
    Notifications notification = new Notifications();
    notification.setUser(user);
    notification.setType(getNotificationType());
    notification.setTitle("Thông báo sản phẩm");

    if (data instanceof Product) {
      Product product = (Product) data;
      notification.setMessage(String.format("Sản phẩm '%s': %s", product.getTitle(), message));
    } else {
      notification.setMessage(message);
    }

    return notification;
  }

  @Override
  public String getNotificationType() {
    return "PRODUCT";
  }
}


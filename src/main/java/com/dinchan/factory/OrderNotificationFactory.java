package com.dinchan.factory;

import com.dinchan.model.Notifications;
import com.dinchan.model.Order;
import com.dinchan.model.User;
import org.springframework.stereotype.Component;

/**
 * Concrete Factory - Tạo notification cho Order
 */
@Component
public class OrderNotificationFactory implements NotificationFactory {

  @Override
  public Notifications createNotification(User user, String message, Object data) {
    Notifications notification = new Notifications();
    notification.setUser(user);
    notification.setType(getNotificationType());
    notification.setTitle("Thông báo đơn hàng");

    if (data instanceof Order) {
      Order order = (Order) data;
      notification.setMessage(String.format("Đơn hàng #%d: %s", order.getId(), message));
    } else {
      notification.setMessage(message);
    }

    return notification;
  }

  @Override
  public String getNotificationType() {
    return "ORDER";
  }
}


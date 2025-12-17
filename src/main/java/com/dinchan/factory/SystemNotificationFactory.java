package com.dinchan.factory;

import com.dinchan.model.Notifications;
import com.dinchan.model.User;
import org.springframework.stereotype.Component;

/**
 * Concrete Factory - Tạo notification hệ thống
 */
@Component
public class SystemNotificationFactory implements NotificationFactory {

  @Override
  public Notifications createNotification(User user, String message, Object data) {
    Notifications notification = new Notifications();
    notification.setUser(user);
    notification.setType(getNotificationType());
    notification.setTitle("Thông báo hệ thống");
    notification.setMessage(message);

    return notification;
  }

  @Override
  public String getNotificationType() {
    return "SYSTEM";
  }
}


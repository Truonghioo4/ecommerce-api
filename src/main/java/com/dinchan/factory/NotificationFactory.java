package com.dinchan.factory;

import com.dinchan.model.Notifications;
import com.dinchan.model.User;

/**
 * Abstract Factory Pattern - Notification Factory Interface
 * Định nghĩa interface cho các factory tạo notification
 */
public interface NotificationFactory {
  Notifications createNotification(User user, String message, Object data);
  String getNotificationType();
}


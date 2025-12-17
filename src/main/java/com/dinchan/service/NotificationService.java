package com.dinchan.service;

import com.dinchan.model.Notifications;
import com.dinchan.model.User;

import java.util.List;

public interface NotificationService {

  /**
   * Tạo notification mới sử dụng Abstract Factory Pattern
   */
  Notifications createNotification(User user, String type, String message, Object data);

  /**
   * Lấy tất cả notification của user
   */
  List<Notifications> getUserNotifications(Long userId);

  /**
   * Lấy notification chưa đọc
   */
  List<Notifications> getUnreadNotifications(Long userId);

  /**
   * Đánh dấu notification đã đọc
   */
  Notifications markAsRead(Long notificationId);

  /**
   * Đánh dấu tất cả notification đã đọc
   */
  void markAllAsRead(Long userId);

  /**
   * Đếm số notification chưa đọc
   */
  long countUnread(Long userId);

  /**
   * Xóa notification
   */
  void deleteNotification(Long notificationId);
}


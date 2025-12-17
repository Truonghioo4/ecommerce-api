package com.dinchan.service.impl;

import com.dinchan.factory.NotificationFactory;
import com.dinchan.factory.NotificationFactoryManager;
import com.dinchan.model.Notifications;
import com.dinchan.model.User;
import com.dinchan.repository.NotificationRepository;
import com.dinchan.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

  private final NotificationRepository notificationRepository;
  private final NotificationFactoryManager factoryManager;

  @Override
  public Notifications createNotification(User user, String type, String message, Object data) {
    // Sử dụng Abstract Factory Pattern để tạo notification
    NotificationFactory factory = factoryManager.getFactory(type);
    Notifications notification = factory.createNotification(user, message, data);
    return notificationRepository.save(notification);
  }

  @Override
  public List<Notifications> getUserNotifications(Long userId) {
    return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
  }

  @Override
  public List<Notifications> getUnreadNotifications(Long userId) {
    return notificationRepository.findByUserIdAndIsRead(userId, false);
  }

  @Override
  public Notifications markAsRead(Long notificationId) {
    Notifications notification = notificationRepository.findById(notificationId)
        .orElseThrow(() -> new RuntimeException("Not found notification"));
    notification.setRead(true);
    return notificationRepository.save(notification);
  }

  @Override
  public void markAllAsRead(Long userId) {
    List<Notifications> unreadNotifications = getUnreadNotifications(userId);
    for (Notifications notification : unreadNotifications) {
      notification.setRead(true);
    }
    notificationRepository.saveAll(unreadNotifications);
  }

  @Override
  public long countUnread(Long userId) {
    return notificationRepository.countByUserIdAndIsRead(userId, false);
  }

  @Override
  public void deleteNotification(Long notificationId) {
    notificationRepository.deleteById(notificationId);
  }
}


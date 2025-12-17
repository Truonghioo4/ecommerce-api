package com.dinchan.repository;

import com.dinchan.model.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notifications, Long> {
  List<Notifications> findByUserId(Long userId);
  List<Notifications> findByUserIdOrderByCreatedAtDesc(Long userId);
  List<Notifications> findByUserIdAndIsRead(Long userId, boolean isRead);
  long countByUserIdAndIsRead(Long userId, boolean isRead);
}


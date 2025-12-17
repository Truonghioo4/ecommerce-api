package com.dinchan.controller;

import com.dinchan.model.Notifications;
import com.dinchan.model.User;
import com.dinchan.request.CreateNotificationRequest;
import com.dinchan.service.NotificationService;
import com.dinchan.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

  private final NotificationService notificationService;
  private final UserService userService;

  /**
   * Lấy tất cả notification của user hiện tại
   */
  @GetMapping
  public ResponseEntity<List<Notifications>> getUserNotifications(
      @RequestHeader("Authorization") String jwt) throws Exception {
    User user = userService.findUserByJwtToken(jwt);
    List<Notifications> notifications = notificationService.getUserNotifications(user.getId());
    return ResponseEntity.ok(notifications);
  }

  /**
   * Lấy notification chưa đọc
   */
  @GetMapping("/unread")
  public ResponseEntity<List<Notifications>> getUnreadNotifications(
      @RequestHeader("Authorization") String jwt) throws Exception {
    User user = userService.findUserByJwtToken(jwt);
    List<Notifications> notifications = notificationService.getUnreadNotifications(user.getId());
    return ResponseEntity.ok(notifications);
  }

  /**
   * Đếm số notification chưa đọc
   */
  @GetMapping("/unread/count")
  public ResponseEntity<Map<String, Long>> countUnread(
      @RequestHeader("Authorization") String jwt) throws Exception {
    User user = userService.findUserByJwtToken(jwt);
    long count = notificationService.countUnread(user.getId());
    Map<String, Long> response = new HashMap<>();
    response.put("count", count);
    return ResponseEntity.ok(response);
  }

  /**
   * Tạo notification mới (sử dụng Abstract Factory Pattern)
   */
  @PostMapping
  public ResponseEntity<Notifications> createNotification(
      @RequestHeader("Authorization") String jwt,
      @RequestBody CreateNotificationRequest request) throws Exception {
    User user = userService.findUserByJwtToken(jwt);
    Notifications notification = notificationService.createNotification(
        user,
        request.getType(),
        request.getMessage(),
        request.getData()
    );
    return ResponseEntity.ok(notification);
  }

  /**
   * Đánh dấu notification đã đọc
   */
  @PutMapping("/{id}/read")
  public ResponseEntity<Notifications> markAsRead(
      @PathVariable Long id,
      @RequestHeader("Authorization") String jwt) throws Exception {
    userService.findUserByJwtToken(jwt); // Verify user authentication
    Notifications notification = notificationService.markAsRead(id);
    return ResponseEntity.ok(notification);
  }

  /**
   * Đánh dấu tất cả notification đã đọc
   */
  @PutMapping("/read-all")
  public ResponseEntity<Map<String, String>> markAllAsRead(
      @RequestHeader("Authorization") String jwt) throws Exception {
    User user = userService.findUserByJwtToken(jwt);
    notificationService.markAllAsRead(user.getId());
    Map<String, String> response = new HashMap<>();
    response.put("message", "Đã đánh dấu tất cả notification là đã đọc");
    return ResponseEntity.ok(response);
  }

  /**
   * Xóa notification
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Map<String, String>> deleteNotification(
      @PathVariable Long id,
      @RequestHeader("Authorization") String jwt) throws Exception {
    userService.findUserByJwtToken(jwt); // Verify user authentication
    notificationService.deleteNotification(id);
    Map<String, String> response = new HashMap<>();
    response.put("message", "Đã xóa notification thành công");
    return ResponseEntity.ok(response);
  }
}


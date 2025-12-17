package com.dinchan.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notifications")
public class Notifications {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String type;
  private String message;
  private String title;

  @ManyToOne
  private User user;

  @JsonProperty("isRead")
  private boolean isRead = false;
  private LocalDateTime createdAt = LocalDateTime.now();
}

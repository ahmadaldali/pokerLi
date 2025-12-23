package com.api.planning.entity;

import com.api.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "estimations")
public class Estimation {
  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_story_id")
  private UserStory userStory;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @Column(name = "date")
  private LocalDateTime date;

  @Column(name = "is_over")
  private Boolean isOver;

  @Column(name = "estimation", nullable = false)
  private Integer estimation;
}

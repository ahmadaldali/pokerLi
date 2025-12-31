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
@Table(name = "estimation_results")
public class EstimationResult {
  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_story_id")
  private UserStory userStory;

  @Column(name = "date")
  private LocalDateTime date;

  @Column(name = "estimation", nullable = false)
  private double estimation;

  @Column(name = "count", nullable = false)
  private int count;

  @Column(name = "total", nullable = false)
  private double total;
}

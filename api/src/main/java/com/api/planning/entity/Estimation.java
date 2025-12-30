package com.api.planning.entity;

import com.api.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

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
  @JoinColumn(name = "user_story_id", nullable = false, updatable = false)
  private UserStory userStory;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "estimation_result_id")
  private EstimationResult estimationResult; // having a value for estimation_result_id means one voting is done for this story

  @Column(name = "date")
  private LocalDateTime date;

  @Column(name = "estimation", nullable = false)
  private double estimation;
}

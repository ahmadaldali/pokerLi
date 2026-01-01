package com.api.planning.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user_stories")
public class UserStory {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", length = 50)
  @org.hibernate.validator.constraints.Length(min = 3, max = 50)
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "sprint_id", nullable = false)
  private Sprint sprint;

  @Column(name = "is_voting_over", nullable = false)
  private Boolean isVotingOver;

  @Column(name = "description", length = 1000)
  private String description;

  @Column(name = "link", length = 500)
  private String link;

  @PrePersist
  private void prePersist() {
    if (isVotingOver == null) {
      isVotingOver = Boolean.FALSE;
    }
  }

  /* Estimations (ongoing + historical) */
  @OneToMany(
    mappedBy = "userStory",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private Set<Estimation> estimations = new HashSet<>();

  /* Estimation history */
  @OneToMany(
    mappedBy = "userStory",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private Set<EstimationResult> estimationResults = new HashSet<>();
}

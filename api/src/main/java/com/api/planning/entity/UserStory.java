package com.api.planning.entity;

import jakarta.persistence.*;
import lombok.*;

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

  private String name;

  @ManyToOne
  @JoinColumn(name = "sprint_id")
  private Sprint sprint;


  @Column(name = "is_voting_over")
  private Boolean isVotingOver;
}

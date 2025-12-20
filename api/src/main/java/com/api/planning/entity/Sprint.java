package com.api.planning.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "sprints")
public class Sprint {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
}

package com.api.planning.entity;

import com.api.planning.dto.request.CardDeckDto;
import com.api.user.entity.User;
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

  @Column(unique = true)
  private String name;

  @Column(name = "card_deck", columnDefinition = "jsonb")
  private String cardDeck;

  @ManyToOne
  @JoinColumn(name = "creator_id")
  private User creator;
}

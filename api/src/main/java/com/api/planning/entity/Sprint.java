package com.api.planning.entity;


import com.api.user.entity.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

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

  @Type(JsonType.class)
  @Column(name = "card_deck", columnDefinition = "jsonb")
  private JsonNode cardDeck;

  @ManyToOne
  @JoinColumn(name = "creator_id")
  private User creator;
}

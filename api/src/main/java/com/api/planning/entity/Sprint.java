package com.api.planning.entity;


import com.api.user.entity.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

  @Column(unique = true, nullable = false)
  private String name;

  @Type(JsonType.class)
  @Column(name = "card_deck", columnDefinition = "jsonb")
  private JsonNode cardDeck;

  private List<Double> sequence;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "creator_id", nullable = false)
  private User creator;

  @OneToMany(
    mappedBy = "sprint",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private List<UserStory> userStories = new ArrayList<>();

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
    name = "participants",
    joinColumns = @JoinColumn(name = "sprint_id"),
    inverseJoinColumns = @JoinColumn(name = "member_id")
  )
  private Set<User> members = new HashSet<>();
}

package com.api.planning.entity;


import com.api.user.entity.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
  private Set<UserStory> userStories = new HashSet<>();

  @OneToMany(mappedBy = "sprint", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private Set<Participant> participants = new HashSet<>();

  @Transient
  public Set<User> getMembers() {
    return participants.stream()
      .map(Participant::getMember)
      .collect(Collectors.toSet());
  }
}

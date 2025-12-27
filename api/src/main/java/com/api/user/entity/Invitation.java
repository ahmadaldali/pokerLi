package com.api.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.id.factory.IdentifierGeneratorFactory;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "invitations")
public class Invitation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String email;

  @ManyToOne
  @JoinColumn(name = "inviter_id", nullable = false)
  private User inviter;

  @Column(unique = true, nullable = false, name = "ref_code")
  private String refCode;

  // Default active = true
  @Column(name = "active", nullable = false)
  private Boolean active;

  @PrePersist
  private void prePersist() {
    if (refCode == null) {
      refCode = UUID.randomUUID().toString().substring(0, 32).toUpperCase();
    }

    if (active == null) {
      active = Boolean.TRUE;
    }
  }

}

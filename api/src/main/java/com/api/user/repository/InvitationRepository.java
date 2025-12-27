package com.api.user.repository;

import com.api.user.entity.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
  boolean existsByEmail(String email);
  boolean existsByRefCode(String refCode);
  Optional<Invitation> findByRefCode(String refCode);
}

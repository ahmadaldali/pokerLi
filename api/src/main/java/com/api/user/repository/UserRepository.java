package com.api.user.repository;

import com.api.user.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);
  Optional<User> findByGuestId(String guestId);
  boolean existsByEmail(String email);
  boolean existsByGuestId(String guestId);

  @EntityGraph(attributePaths = "inviter")
  Optional<User> findById(Long id);
}

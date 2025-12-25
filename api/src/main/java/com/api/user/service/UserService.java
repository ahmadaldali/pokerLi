package com.api.user.service;

import com.api.common.enums.UserRole;
import com.api.common.exception.ForbiddenException;
import com.api.user.entity.User;
import com.api.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email).or(() -> userRepository.findByGuestId(email)).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

    return new CustomUserDetails(user);
  }

  public User getUser(Long id) {
    return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  public void ensureAdmin(Long id) {
    User user = this.getUser(id);
    if (user.getRole() != UserRole.ADMIN) {
      throw new ForbiddenException();
    }
  }

}

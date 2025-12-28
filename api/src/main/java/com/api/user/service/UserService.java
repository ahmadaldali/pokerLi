package com.api.user.service;


import com.api.common.dto.SuccessResponse;
import com.api.common.enums.UserRole;
import com.api.common.exception.ForbiddenException;
import com.api.common.exception.ValidationException;
import com.api.user.entity.Invitation;
import com.api.user.entity.User;
import com.api.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final InvitationService  invitationService;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email).or(() -> userRepository.findByGuestId(email)).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

    return new CustomUserDetails(user);
  }

  public SuccessResponse inviteUser(String email, Long userId) {
    ensureAdmin(userId);

    return invitationService.inviteUser(email, userId);
  }

  // HELPERS
  public void ensureAdmin(Long id) {
    User user = this.getUser(id);
    if (user.getRole() != UserRole.ADMIN) {
      throw new ForbiddenException();
    }
  }

  public void ensureNotExistingEmail(String email) {
    if (userRepository.existsByEmail(email)) {
      throw new ValidationException("error.email.exist");
    }
  }

  public void ensureNotExistingGuestId(String guestId) {
    if (userRepository.existsByGuestId(guestId)) {
      throw new ValidationException("error.guest_id.exist");
    }
  }

  public User getUser(Long id) {
    return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  public User getUserByEmail(String email) {
    return userRepository.findByEmail(email).orElseThrow(() -> new ValidationException("error.login.user_notFound"));
  }

  public User createUser(String name, UserRole role, String email, String password, String guestId, User inviter) {
    User.UserBuilder builder = User.builder()
      .name(name)
      .role(role)
      .email(email)
      .password(password)
      .guestId(guestId)
      .inviter(inviter);

    User user = builder.build();
    userRepository.save(user);

    return user;
  }

}

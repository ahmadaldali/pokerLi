package com.api.auth.service;

import com.api.auth.dto.AuthResponse;
import com.api.auth.jwt.JwtService;
import com.api.common.enums.UserRole;
import com.api.common.exception.ForbiddenException;
import com.api.common.exception.ValidationException;
import com.api.user.entity.Invitation;
import com.api.user.entity.User;
import com.api.user.service.InvitationService;
import com.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final UserService userService;
  private final InvitationService invitationService;


  public AuthResponse register(String email, String name, String password, String refCode) {
    userService.ensureNotExistingEmail(email);

    User user;
    if (refCode != null) {
      Invitation invitation = invitationService.getInvitationByCode(refCode);

      if (!invitation.getEmail().equals(email)) {
        throw new ForbiddenException();
      }

      invitationService.ensureActive(invitation);

      user = userService.createUser(name, UserRole.MEMBER, email, passwordEncoder.encode(password), null, invitationService.getInvitationByCode(refCode).getInviter());

      invitationService.deactivateInvitation(invitation.getRefCode());
    } else {
      user = userService.createUser(name, UserRole.ADMIN, email, passwordEncoder.encode(password), null, null);
    }

    String token = jwtService.generateToken(user.getEmail(), user.getId());

    return new AuthResponse(token);
  }

  public AuthResponse login(String email, String password) {
    User user = userService.getUserByEmail(email)
      .orElseThrow(() -> new ValidationException("error.login.invalid_credentials"));

    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new ValidationException("error.login.invalid_credentials");
    }

    String token = jwtService.generateToken(user.getEmail(), user.getId());

    return new AuthResponse(token);
  }


  public AuthResponse createGuest(String name, String guestId) {
    userService.ensureNotExistingGuestId(guestId);

    User user = userService.createUser(name, UserRole.GUEST, null, null, guestId, null);

    // generate the token based on the guestId instead of email
    String token = jwtService.generateToken(user.getGuestId(), user.getId());

    return new AuthResponse(token);
  }
}

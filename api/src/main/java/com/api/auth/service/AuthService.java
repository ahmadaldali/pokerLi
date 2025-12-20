package com.api.auth.service;

import com.api.auth.dto.AuthResponse;
import com.api.auth.jwt.JwtService;
import com.api.common.enums.UserRole;
import com.api.common.exception.ValidationException;
import com.api.user.entity.User;
import com.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;

  public AuthResponse register(String email, String name, String password) {
    if (userRepository.existsByEmail(email)) {
      throw new ValidationException("error.email.exist");
    }

    User user = User.builder()
      .name(name)
      .email(email)
      .password(passwordEncoder.encode(password))
      .role(UserRole.ADMIN)
      .build();
    userRepository.save(user);

    String token = jwtService.generateToken(user.getEmail(), user.getId());

    return new AuthResponse(token);
  }

  public AuthResponse login(String email, String password) {
    User user = userRepository.findByEmail(email)
      .orElseThrow(() -> new ValidationException("error.login.user_notfound"));

    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new ValidationException("error.login.invalid_credentials");
    }

    String token = jwtService.generateToken(user.getEmail(), user.getId());

    return new AuthResponse(token);
  }
}

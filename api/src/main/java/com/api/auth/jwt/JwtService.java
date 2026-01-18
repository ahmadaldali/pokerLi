package com.api.auth.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

  private static final String SECRET_KEY =
    "5b9b16c5e6340204852dcfedf1cc6e5e";

  private Key getSigningKey() {
    return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
  }

  /* ================= TOKEN GENERATION ================= */

  public String generateToken(String email, Long userId) {
    return Jwts.builder()
      .setSubject(email)
      .claim("userId", userId)
      .setIssuedAt(new Date())
      .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 7)) // 7 days
      .signWith(getSigningKey())
      .compact();
  }

  /* ================= VALIDATION ================= */

  public boolean isTokenValid(String token, String email)
    throws AuthenticationException {

    final String tokenEmail = extractEmail(token);

    if (!email.equals(tokenEmail)) {
      throw new BadCredentialsException("JWT subject mismatch");
    }

    if (isTokenExpired(token)) {
      throw new BadCredentialsException("JWT expired");
    }

    return true;
  }

  /* ================= EXTRACTION ================= */

  public String extractEmail(String token)
    throws AuthenticationException {

    try {
      return parseClaims(token).getSubject();
    } catch (ExpiredJwtException ex) {
      throw new BadCredentialsException("JWT expired", ex);
    } catch (JwtException | IllegalArgumentException ex) {
      throw new BadCredentialsException("Invalid JWT token", ex);
    }
  }

  public Long extractUserId(String token)
    throws AuthenticationException {

    Claims claims = parseClaims(token);
    Object userId = claims.get("userId");

    if (userId instanceof Integer i) {
      return i.longValue();
    }
    if (userId instanceof Long l) {
      return l;
    }

    throw new BadCredentialsException("Invalid userId claim");
  }

  /* ================= INTERNAL ================= */

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return parseClaims(token).getExpiration();
  }

  private Claims parseClaims(String token)
    throws AuthenticationException {

    try {
      return Jwts.parser()
        .setSigningKey(getSigningKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
    } catch (ExpiredJwtException ex) {
      throw new BadCredentialsException("JWT expired", ex);
    } catch (JwtException | IllegalArgumentException ex) {
      throw new BadCredentialsException("Invalid JWT token", ex);
    }
  }
}

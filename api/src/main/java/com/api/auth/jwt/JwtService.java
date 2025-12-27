package com.api.auth.jwt;

import com.api.common.exception.UnAuthorizedException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

  private static final String SECRET_KEY = "5b9b16c5e6340204852dcfedf1cc6e5e";

  private Key getSigningKey() {
    return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
  }

  public String generateToken(String email, Long userId) {
    return Jwts.builder()
      .setSubject(email)
      .claim("userId", userId)
      .setIssuedAt(new Date())
      .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24 hours
      .signWith(getSigningKey())
      .compact();
  }

  public boolean isTokenValid(String token, String email) {
    try {
      return email.equals(extractEmail(token)) && !isTokenExpired(token);
    } catch (Exception e) {
      throw new UnAuthorizedException();
    }

  }

  public String extractEmail(String token) {
    try {
      return Jwts.parser()
        .setSigningKey(getSigningKey())
        .build()
        .parseSignedClaims(token)
        .getPayload().getSubject();
    } catch (Exception e) {
      throw new UnAuthorizedException();
    }
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    try {
      return Jwts.parser()
        .setSigningKey(getSigningKey())
        .build()
        .parseSignedClaims(token)
        .getPayload().getExpiration();
    } catch (Exception e) {
      throw new UnAuthorizedException();
    }
  }

  public Long extractUserId(String token) {
    Claims claims = Jwts.parser()
      .setSigningKey(getSigningKey())
      .build()
      .parseClaimsJws(token)
      .getBody();

    Object userIdObj = claims.get("userId");
    // userId might be stored as Integer or Long depending on serialization, so handle accordingly
    if (userIdObj instanceof Integer) {
      return ((Integer) userIdObj).longValue();
    } else if (userIdObj instanceof Long) {
      return (Long) userIdObj;
    } else {
      throw new IllegalArgumentException("Invalid userId claim type");
    }
  }

}

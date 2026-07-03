package com.example.demo.service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

  private final String secret;
  private final long expirationMs;

  public JwtService(
    @Value("${app.jwt.secret}") String secret,
    @Value("${app.jwt.expiration-ms}") long expirationMs
  ) {
    this.secret = secret;
    this.expirationMs = expirationMs;
  }

  public String generateToken(String email, String role) {
    Date now = new Date();
    Date expiration = new Date(now.getTime() + expirationMs);

    return Jwts
      .builder()
      .subject(email)
      .claim("role", role)
      .issuedAt(now)
      .expiration(expiration)
      .signWith(getSigningKey())
      .compact();
  }

  public String extractEmail(String token) {
    return extractClaims(token).getSubject();
  }

  public String extractRole(String token) {
    return extractClaims(token).get("role", String.class);
  }

  public boolean isTokenValid(String token) {
    try {
      extractClaims(token);
      return true;
    } catch (Exception exception) {
      return false;
    }
  }

  private Claims extractClaims(String token) {
    return Jwts
      .parser()
      .verifyWith((javax.crypto.SecretKey) getSigningKey())
      .build()
      .parseSignedClaims(token)
      .getPayload();
  }

  private Key getSigningKey() {
    return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
  }
}
package com.todo.app.config;

import com.todo.app.domain.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  @Value("${app.jwt.secret}")
  private String secret;

  @Value("${app.jwt.expiration-ms}")
  private long expiration;

  public String generateToken(User user) {
    long now = System.currentTimeMillis();

    return Jwts.builder()
        .subject(user.getUsername())
        .claim("uid", user.getId())
        .issuedAt(new Date(now))
        .expiration(new Date(now + expiration))
        .signWith(signingKey())
        .compact();
  }

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public boolean isTokenValid(String token, UserDetails userDetails) {
    String username = extractUsername(token);
    return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    return extractClaim(token, Claims::getExpiration).before(new Date());
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
    Claims claims = Jwts.parser()
        .verifyWith(signingKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();

    return claimResolver.apply(claims);
  }

  private SecretKey signingKey() {
    return Keys.hmacShaKeyFor(resolveSigningKeyBytes());
  }

  private byte[] resolveSigningKeyBytes() {
    try {
      byte[] decoded = Decoders.BASE64.decode(secret);
      if (decoded.length >= 32) {
        return decoded;
      }
    } catch (RuntimeException ignored) {
      // Fallback below supports plain text secrets from hosting platforms.
    }

    try {
      return MessageDigest.getInstance("SHA-256")
          .digest(secret.getBytes(StandardCharsets.UTF_8));
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException("SHA-256 algorithm unavailable", e);
    }
  }
}

package hospital.Hospisync_backend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {

    @Value("${app.jwt.secret}")
    private String secret;

    private static final long EXPIRATION_MS = 86400000L; // 24 hours

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String email, Long hospitalId, Long userId, String role) {
        return Jwts.builder()
                .subject(email)
                .claim("hospitalId", hospitalId)
                .claim("user_id", userId)
                .claim("roles", List.of(role != null && role.startsWith("ROLE_") ? role : "ROLE_" + role))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(getSigningKey())
                .compact();
    }

    public String getEmailFromToken(String token) {
        return getClaims(token).getPayload().getSubject();
    }

    public Long getHospitalIdFromToken(String token) {
        Object id = getClaims(token).getPayload().get("hospitalId");
        if (id instanceof Number) {
            return ((Number) id).longValue();
        }
        return null;
    }

    public Long getUserIdFromToken(String token) {
        Object id = getClaims(token).getPayload().get("user_id");
        if (id instanceof Number) {
            return ((Number) id).longValue();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<String> getRolesFromToken(String token) {
        return getClaims(token).getPayload().get("roles", List.class);
    }

    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Jws<Claims> getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token);
    }
}

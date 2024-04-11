package com.example.server.auth;

import com.example.server.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@AllArgsConstructor
public class JwtUtil {
    private final UserRepository userRepository;
    private static final String ACCESS_SECRET_KEY = "accessSecretKey";
    private static final String CONFIRMATION_SECRET_KEY = "confirmationSecretKey";
    private static final long EXPIRATION_TIME = 1000 * 60 * 15;
    private static final String TOKEN_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    public String generateAccessToken(String email) {
        return generateToken(email, ACCESS_SECRET_KEY);
    }

    public String generateConfirmationToken(String email) {
        return generateToken(email, CONFIRMATION_SECRET_KEY);
    }

    public String extractEmailFromAccessToken(String token) {
        return extractEmail(token, ACCESS_SECRET_KEY);
    }

    public String extractEmailFromConfirmationToken(String token) {
        return extractEmail(token, CONFIRMATION_SECRET_KEY);
    }
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(TOKEN_HEADER);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    private Claims extractAllClaims(String token, String secretKey) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    private String extractEmail(String token, String secretKey) {
        return extractAllClaims(token, secretKey).getSubject();
    }

    private String generateToken(String email, String secretKey) {
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
}

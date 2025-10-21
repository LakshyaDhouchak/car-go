package com.lakshya.car_go.Security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
@RequiredArgsConstructor
public class AuthService {

    // define the properties
    @Value("${application.security.jwt.secret-key}")
    private final String SecretCode;
    @Value("${application.security.jwt.expiration}")
    private final Long jwtExpiration;

    // define the  generateToken() methord
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey(), Jwts.SIG.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, new ClaimResolver<String>() {
            @Override
            public String resolve(Claims claims) {
                return claims.getSubject();
            }
        });
    }

    private <T> T extractClaim(String token, ClaimResolver<T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.resolve(claims); // Call the resolve method
    }

    private interface ClaimResolver<T> {
        T resolve(Claims claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, new ClaimResolver<Date>() {
            @Override
            public Date resolve(Claims claims) {
                return claims.getExpiration();
            }
        });
    }

}

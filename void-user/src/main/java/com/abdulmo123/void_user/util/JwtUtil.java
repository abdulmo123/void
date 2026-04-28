package com.abdulmo123.void_user.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    private final String SECRET_KEY = "58ad4369948ead5b61bcfa2c2ee7d5918e2509be3bc680d3c823a22ae065098a";

    /**
     * Extracts the username (email) from the given JWT token.
     *
     * @param token the JWT token
     * @return the username stored in the token's subject claim
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Generates a JWT token for the given user with no extra claims.
     *
     * @param userDetails the authenticated user's details
     * @return a signed JWT token string
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Generates a JWT token for the given user with additional custom claims.
     * Token is signed with HS256 and expires 24 hours from the time of creation.
     *
     * @param claims      a map of additional claims to embed in the token
     * @param userDetails the authenticated user's details
     * @return a signed JWT token string
     */
    public String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts
                .builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) // 24hrs + 1K ms
                .signWith(getSignInKey(), Jwts.SIG.HS256)
                .compact();
    }

    /**
     * Validates the given JWT token against the provided user details.
     * A token is valid if the username matches and the token has not expired.
     *
     * @param token       the JWT token to validate
     * @param userDetails the user details to validate against
     * @return true if the token is valid, false otherwise
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Checks whether the given JWT token has expired.
     *
     * @param token the JWT token
     * @return true if the token has expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from the given JWT token.
     *
     * @param token the JWT token
     * @return the expiration date of the token
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts a specific claim from the given JWT token using the provided resolver function.
     *
     * @param token          the JWT token
     * @param claimsResolver a function that extracts the desired claim from the Claims object
     * @param <T>            the type of the claim to extract
     * @return the extracted claim value
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Parses and returns all claims from the given JWT token.
     * Verifies the token signature using the application's secret key.
     *
     * @param token the JWT token
     * @return the Claims object containing all claims embedded in the token
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Decodes the Base64 encoded secret key and returns it as a SecretKey
     * for use in signing and verifying JWT tokens with HMAC-SHA256.
     *
     * @return the SecretKey derived from the application's secret key config
     */
    private SecretKey getSignInKey() {
        byte [] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

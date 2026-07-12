package lk.ijse.cmjd.research_tracker.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Utility class for JWT token generation, validation, and claim extraction.
 */
@Component
public class JwtUtil {

    /** Secret key for signing JWT tokens (loaded from application.properties) */
    @Value("${jwt.secret}")
    private String secretKey;

    /** Token expiration time in milliseconds (loaded from application.properties) */
    @Value("${jwt.expiration}")
    private long jwtExpiration;

    /**
     * Extract the username (subject) from a JWT token.
     *
     * @param token the JWT token
     * @return the username/subject
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extract the expiration date from a JWT token.
     *
     * @param token the JWT token
     * @return the expiration date
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extract a specific claim from a JWT token.
     *
     * @param token          the JWT token
     * @param claimsResolver function to resolve the desired claim
     * @param <T>            type of the claim
     * @return the resolved claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Generate a JWT token for the given user details.
     *
     * @param userDetails Spring Security UserDetails
     * @return the generated JWT token string
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> extraClaims = new HashMap<>();
        // Add the user's role as a custom claim
        extraClaims.put("role", userDetails.getAuthorities().iterator().next().getAuthority());
        return generateToken(extraClaims, userDetails);
    }

    /**
     * Generate a JWT token with additional claims.
     *
     * @param extraClaims additional claims to include
     * @param userDetails Spring Security UserDetails
     * @return the generated JWT token string
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Validate a JWT token against the given user details.
     *
     * @param token       the JWT token
     * @param userDetails Spring Security UserDetails
     * @return true if the token is valid and belongs to the user
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Check if a JWT token has expired.
     *
     * @param token the JWT token
     * @return true if expired
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extract all claims from a JWT token.
     *
     * @param token the JWT token
     * @return all claims
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Get the signing key derived from the base64-encoded secret.
     *
     * @return the HMAC-SHA signing key
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

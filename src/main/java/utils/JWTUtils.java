package utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by borikhankozha on 10/26/17.
 */
public class JWTUtils {

    static final String CLAIM_KEY_USERNAME = "sub";
    static final String CLAIM_KEY_CREATED = "created";
    private static final String SECRET = "itIsInGitHub";
    private static final Long EXPIRATION = 2592000L;

    public static String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }

        return username;
    }

    public static Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
        }

        return created;
    }

    public static Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }

        return expiration;
    }

    private static Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }

        return claims;
    }

    private static Date generateExpirationDate() {

        return new Date(System.currentTimeMillis() + EXPIRATION * 1000);
    }

    private static Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);

        return expiration.before(new Date());
    }

    public static String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, email);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public static Boolean isValidToken(String token, String email) {
        final String username = getUsernameFromToken(token);

        return username != null && (username.equals(email) && !isTokenExpired(token));
    }
}

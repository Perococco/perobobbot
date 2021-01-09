package perobobbot.server.config.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.web.authentication.www.NonceExpiredException;
import perobobbot.data.com.User;
import perobobbot.data.service.UserProvider;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @author Perococco
 */
@RequiredArgsConstructor
public class JWTokenManager {

    @NonNull
    private final String base64EncodedKey;

    @NonNull
    private final String issuer;

    @NonNull
    private final UserProvider userProvider;

    public @NonNull String createJWToken(@NonNull String login) {
        final var user = userProvider.getUser(login);
        final var now = Instant.now();
        final var expiration = now.plus(30, ChronoUnit.DAYS);

        final Claims claims = Jwts.claims();
        claims.setSubject(user.getLogin())
              .setIssuedAt(Date.from(now))
              .setId(user.getJwtClaim())
              .setIssuer(issuer)
              .setExpiration(Date.from(expiration));

        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, base64EncodedKey).compact();
    }

    private Claims extractClaim(String token) {
        try {
            return Jwts.parser()
                       .setSigningKey(base64EncodedKey)
                       .parseClaimsJws(token)
                       .getBody();
        } catch (JwtException e) {
            throw new BadCredentialsException("Invalid JWT Token", e);
        }
    }

    public @NonNull User getUserFromToken(@NonNull String token) {
        final Claims claims = extractClaim(token);
        checkIssuer(claims);
        checkExpirationDate(claims);
        return checkUser(claims);
    }

    private void checkIssuer(Claims claims) {
        if (!claims.getIssuer().equals(issuer)) {
            throw new InvalidJWTIssuer("Invalid issuer : "+claims.getIssuer());
        }
    }

    private void checkExpirationDate(Claims claims) {
        final Date now = Date.from(Instant.now());
        if (claims.getExpiration().before(now)) {
            throw new CredentialsExpiredException("JWT token has expired");
        }
    }

    private @NonNull User checkUser(Claims claims) {
        final String login = claims.getSubject();
        final var user = userProvider.getUser(login);

        if (!user.getJwtClaim().equals(claims.getId())) {
            throw new NonceExpiredException("This claims has been revoked");
        }

        return user;
    }
}

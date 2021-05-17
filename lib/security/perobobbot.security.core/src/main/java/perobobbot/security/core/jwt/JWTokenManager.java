package perobobbot.security.core.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.web.authentication.www.NonceExpiredException;
import perobobbot.lang.Instants;
import perobobbot.security.com.JwtInfo;
import perobobbot.security.com.User;
import perobobbot.security.core.UserProvider;

import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @author perococco
 */
@RequiredArgsConstructor
public class JWTokenManager {

    @NonNull
    private final String base64EncodedKey;

    @NonNull
    private final String issuer;

    @NonNull
    private final UserProvider userProvider;

    @NonNull
    private final Instants instants;

    public @NonNull JwtInfo createJwtInfo(@NonNull String login) {
        final var user = userProvider.getUserDetails(login);
        final var jwtClaim = user.getJwtClaim();
        final var now = instants.now();
        final var expiration = now.plus(30, ChronoUnit.DAYS);

        final Claims claims = Jwts.claims();
        claims.setSubject(login)
              .setIssuedAt(Date.from(now))
              .setId(jwtClaim)
              .setIssuer(issuer)
              .setExpiration(Date.from(expiration));

        final String token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, base64EncodedKey).compact();
        return new JwtInfo(token,user.simplify());
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
        final Date now = Date.from(instants.now());
        if (claims.getExpiration().before(now)) {
            throw new CredentialsExpiredException("JWT token has expired");
        }
    }

    private @NonNull perobobbot.security.com.User checkUser(Claims claims) {
        final String login = claims.getSubject();
        final var user = userProvider.getUserDetails(login);

        if (user.isDeactivated()) {
            throw new DisabledException("User has been deactivated");
        }

        if (!user.getJwtClaim().equals(claims.getId())) {
            throw new NonceExpiredException("This claims has been revoked");
        }

        return user;
    }
}

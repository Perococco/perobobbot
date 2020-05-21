package perobobbot.server.config.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.NonceExpiredException;
import perobobbot.data.domain.JwtTokenGenerator;
import perobobbot.data.domain.User;
import perobobbot.data.jpa.repository.UserRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @author Perococco
 */
@RequiredArgsConstructor
public class JwtTokenManager implements JwtTokenGenerator {

    @NonNull
    private final String base64EncodedKey;

    @NonNull
    private final String issuer;

    @NonNull
    private final UserRepository userRepository;

    @Override
    public @NonNull String createTokenFromUser(@NonNull User user) {
        final Instant now = Instant.now();
        final Instant expiration = now.plus(7, ChronoUnit.DAYS);

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

    public Authentication extractClaimAndValidate(String token) {
        final Claims claims = extractClaim(token);
        checkIssuer(claims);
        checkExpirationDate(claims);

        final User user = checkUser(claims);
        final JwtAuthentication authentication = JwtAuthentication.create(user);
        authentication.setAuthenticated(true);
        return authentication;
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

    private User checkUser(Claims claims) {
        final String login = claims.getSubject();
        final User user = userRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("User with login '" + login + "' does not exists"));

        if (!user.getJwtClaim().equals(claims.getId())) {
            throw new NonceExpiredException("This claims has been revoked");
        }

        return user;
    }
}

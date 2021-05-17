package perobobbot.server.config.security.ws;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UriComponentsBuilder;
import perobobbot.lang.ThrowableTool;
import perobobbot.security.core.jwt.JWTokenManager;
import perobobbot.server.config.security.TokenBasedAuthenticationFilter;
import perobobbot.server.config.security.jwt.JwtAuthentication;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author perococco
 */
@Log4j2
public class WSAuthenticationFilter extends TokenBasedAuthenticationFilter {

    public static final String ACCESS_TOKEN_NAME = "access_token";

    public WSAuthenticationFilter(@NonNull JWTokenManager jwTokenManager) {
        super(jwTokenManager);
    }

    public @NonNull Optional<String> retrieveAccessTokenFromRequest(HttpServletRequest request) {
        if (request.getQueryString() == null || !request.getQueryString().contains(ACCESS_TOKEN_NAME)) {
            return Optional.empty();
        }
        final var uri = String.join("?", request.getRequestURI(), request.getQueryString());
        final var uriComponents = UriComponentsBuilder.fromUriString(uri).build();
        final var token = uriComponents.getQueryParams().getFirst(ACCESS_TOKEN_NAME);

        return Optional.ofNullable(token);
    }


}

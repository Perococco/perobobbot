package perobobbot.server.config.security.ws;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.util.UriComponentsBuilder;
import perobobbot.security.core.jwt.JWTokenManager;
import perobobbot.server.config.security.TokenBasedAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @author perococco
 */
@Log4j2
public class WSAuthenticationFilter extends TokenBasedAuthenticationFilter {

    public static final String ACCESS_TOKEN_NAME = "access_token";

    public WSAuthenticationFilter(@NonNull JWTokenManager jwTokenManager) {
        super(jwTokenManager);
    }

    public @NonNull Optional<String> retrieveAccessTokenFromRequest(@NonNull HttpServletRequest request) {
        if (request.getQueryString() == null || !request.getQueryString().contains(ACCESS_TOKEN_NAME)) {
            return Optional.empty();
        }
        final var uri = String.join("?", request.getRequestURI(), request.getQueryString());
        final var uriComponents = UriComponentsBuilder.fromUriString(uri).build();
        final var token = uriComponents.getQueryParams().getFirst(ACCESS_TOKEN_NAME);

        return Optional.ofNullable(token);
    }


}

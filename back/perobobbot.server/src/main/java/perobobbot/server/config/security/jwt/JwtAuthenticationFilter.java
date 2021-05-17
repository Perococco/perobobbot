package perobobbot.server.config.security.jwt;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import perobobbot.lang.ThrowableTool;
import perobobbot.security.core.jwt.JWTokenManager;
import perobobbot.server.config.security.TokenBasedAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * @author perococco
 */
public class JwtAuthenticationFilter extends TokenBasedAuthenticationFilter {


    public static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    public static final String BEARER_PREFIX_TOKEN = "bearer ";

    public JwtAuthenticationFilter(@NonNull JWTokenManager jwTokenManager) {
        super(jwTokenManager);
    }

    @Override
    protected @NonNull Optional<String> retrieveAccessTokenFromRequest(@NonNull HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(AUTHORIZATION_HEADER_NAME))
                .filter(h -> h.toLowerCase().startsWith(BEARER_PREFIX_TOKEN))
                .map(h -> h.substring(BEARER_PREFIX_TOKEN.length()));
    }

}

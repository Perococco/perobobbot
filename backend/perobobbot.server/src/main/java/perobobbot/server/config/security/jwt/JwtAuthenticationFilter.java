package perobobbot.server.config.security.jwt;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.util.MessageSupplier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import perobobbot.lang.ThrowableTool;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Perococco
 */
@Log4j2
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final JWTokenManager jwtTokenService;

    public static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    public static final String BEARER_PREFIX_TOKEN = "bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader(AUTHORIZATION_HEADER_NAME);

        if (isHeaderNullOrDoesNotMatchBearer(header)) {
            filterChain.doFilter(request,response);
            return;
        }

        try {
            final Authentication authentication = this.extractAuthenticationFromJWTToken(header);
            if (LOG.isDebugEnabled()) {
                LOG.debug("JWT Token accepted for user {}",authentication.getName());
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (AuthenticationException e) {
            LOG.warn("Authentication with JWT Token failed : {} ", ThrowableTool.formCauseMessageChain(e));
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request,response);
    }

    private boolean isHeaderNullOrDoesNotMatchBearer(String header) {
        return header == null || !header.toLowerCase().startsWith(BEARER_PREFIX_TOKEN);
    }

    private @NonNull Authentication extractAuthenticationFromJWTToken(String headerValue) {
        final var user = jwtTokenService.getUserFromToken(headerValue.substring(BEARER_PREFIX_TOKEN.length()));
        return JwtAuthentication.create(user);
    }

}

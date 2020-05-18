package perobobbot.server.config.security.jwt;

import lombok.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Perococco
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final JwtTokenManager jwtTokenManager;

    public static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    public static final String BEARER_PREFIX_TOKEN = "bearer ";

    public JwtAuthenticationFilter(JwtTokenManager jwtTokenManager) {
        this.jwtTokenManager = jwtTokenManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader(AUTHORIZATION_HEADER_NAME);

        if (isHeaderNullOrNotMatchBearer(header)) {
            filterChain.doFilter(request,response);
            return;
        }

        try {
            final Authentication authentication = this.extractAuthenticationFromJWTToken(header);
            if (logger.isDebugEnabled()) {
                logger.debug("JWT Token accepted for user "+authentication.getName());
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (AuthenticationException e) {
            this.logger.warn("Authentication with JWT Token failed ",e);
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request,response);

    }

    private boolean isHeaderNullOrNotMatchBearer(String header) {
        return header == null || !header.toLowerCase().startsWith(BEARER_PREFIX_TOKEN);
    }

    private Authentication extractAuthenticationFromJWTToken(String headerValue) {
        return jwtTokenManager.extractClaimAndValidate(headerValue.substring(BEARER_PREFIX_TOKEN.length()));
    }

}
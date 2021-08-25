package perobobbot.server.config.security;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import perobobbot.lang.ThrowableTool;
import perobobbot.security.core.jwt.JWTokenManager;
import perobobbot.server.config.security.jwt.JwtAuthentication;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Log4j2
public abstract class TokenBasedAuthenticationFilter extends OncePerRequestFilter {

    private final @NonNull JWTokenManager jwTokenManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        retrieveAccessTokenFromRequest(request)
                .ifPresent(this::performAuthentication);

        filterChain.doFilter(request,response);
    }

    private void performAuthentication(@NonNull String token) {
        try {
            final Authentication authentication = JwtAuthentication.create(jwTokenManager.getUserFromToken(token));
            if (LOG.isDebugEnabled()) {
                LOG.debug("JWT Token accepted for user {}",authentication.getName());
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (AuthenticationException e) {
//            LOG.warn("Authentication with JWT Token failed : {} ", ThrowableTool.formCauseMessageChain(e));
            SecurityContextHolder.clearContext();
        }

    }

    protected abstract @NonNull Optional<String> retrieveAccessTokenFromRequest(@NonNull HttpServletRequest request);
}

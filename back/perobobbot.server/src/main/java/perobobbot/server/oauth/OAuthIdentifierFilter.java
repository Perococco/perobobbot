package perobobbot.server.oauth;

import lombok.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import perobobbot.lang.CastTool;
import perobobbot.lang.Caster;
import perobobbot.oauth.LoginTokenIdentifier;
import perobobbot.oauth.OAuthContextHolder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class OAuthIdentifierFilter extends OncePerRequestFilter {

    public static final Caster<UserDetails> USER_DETAILS_CASTER = CastTool.caster(UserDetails.class);

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .flatMap(USER_DETAILS_CASTER)
                .map(UserDetails::getUsername)
                .map(LoginTokenIdentifier::new)
                .ifPresent(OAuthContextHolder.getContext()::setTokenIdentifier);
        filterChain.doFilter(request,response);
    }
}

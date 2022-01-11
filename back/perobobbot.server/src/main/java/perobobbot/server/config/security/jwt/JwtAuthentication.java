package perobobbot.server.config.security.jwt;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import perobobbot.security.com.BotUser;
import perobobbot.security.com.Authentication;
import perobobbot.security.com.User;
import perobobbot.server.config.security.ExtractorOfGrantedAuthorities;

import java.util.Collection;

/**
 * @author perococco
 */
public class JwtAuthentication extends AbstractAuthenticationToken {

    public static @NonNull JwtAuthentication create(@NonNull User user) {
        final ImmutableList<GrantedAuthority> authorities = ExtractorOfGrantedAuthorities.extract(user);
        final var authentication = new JwtAuthentication(user,authorities);
        authentication.setAuthenticated(true);
        return authentication;
    }

    private final JwtUser user;

    public JwtAuthentication(User user, ImmutableList<GrantedAuthority> authorities) {
        super(authorities);
        this.user = new JwtUser(user.getUsername(), user.getAuthentication(), ExtractorOfGrantedAuthorities.extract(user));
    }

    @Override
    public Object getCredentials() {
        return user.getCredentials();
    }

    @Override
    public BotUser getPrincipal() {
        return user;
    }

    @RequiredArgsConstructor
    private static class JwtUser implements BotUser {

        @Getter
        private final @NonNull String username;

        @Getter
        private final @NonNull Authentication credentials;

        @Getter
        private final Collection<? extends GrantedAuthority> authorities;


    }
}

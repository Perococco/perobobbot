package perobobbot.server.config.security.jwt;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import perobobbot.security.com.User;
import perobobbot.server.config.security.ExtractorOfGrantedAuthorities;

/**
 * @author Perococco
 */
public class JwtAuthentication extends AbstractAuthenticationToken {

    public static @NonNull JwtAuthentication create(@NonNull User user) {
        final ImmutableList<GrantedAuthority> authorities = ExtractorOfGrantedAuthorities.extract(user);
        final UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), authorities);
        final var authentication = new JwtAuthentication(userDetails);
        authentication.setAuthenticated(true);
        return authentication;
    }

    private final UserDetails userDetails;

    public JwtAuthentication(UserDetails userDetails) {
        super(userDetails.getAuthorities());
        this.userDetails = userDetails;
    }

    @Override
    public String getCredentials() {
        return userDetails.getPassword();
    }

    @Override
    public UserDetails getPrincipal() {
        return userDetails;
    }
}

package perobobbot.server.config.security.jwt;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import perobobbot.data.domain.User;
import perobobbot.server.config.security.ExtractorOfGrantedAuthorities;

import java.util.Set;

/**
 * @author Perococco
 */
public class JwtAuthentication extends AbstractAuthenticationToken {

    public static JwtAuthentication create(@NonNull User user) {
        final ImmutableList<GrantedAuthority> authorities = ExtractorOfGrantedAuthorities.extract(user);
        final UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), authorities);
        return new JwtAuthentication(userDetails);
    }

    private final UserDetails userDetails;

    public JwtAuthentication(UserDetails userDetails) {
        super(userDetails.getAuthorities());
        this.userDetails = userDetails;
    }

    @Override
    public Object getCredentials() {
        return userDetails.getPassword();
    }

    @Override
    public Object getPrincipal() {
        return userDetails;
    }
}

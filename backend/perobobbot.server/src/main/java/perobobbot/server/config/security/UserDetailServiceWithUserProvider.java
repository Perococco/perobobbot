package perobobbot.server.config.security;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import perobobbot.security.core.UserProvider;

/**
 * @author Perococco
 */
@RequiredArgsConstructor
public class UserDetailServiceWithUserProvider implements UserDetailsService {

    @NonNull
    private final UserProvider userProvider;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        final var user = userProvider.getUser(login);
        return PerobobbotUserDetails.with(user);
    }
}

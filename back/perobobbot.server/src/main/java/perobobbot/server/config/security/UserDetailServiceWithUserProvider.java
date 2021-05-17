package perobobbot.server.config.security;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import perobobbot.security.core.UserProvider;

/**
 * @author perococco
 */
@RequiredArgsConstructor
public class UserDetailServiceWithUserProvider implements UserDetailsService {

    @NonNull
    private final UserProvider userProvider;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        final var user = userProvider.getUserDetails(login);
        return PerobobbotUserDetails.with(user);
    }
}

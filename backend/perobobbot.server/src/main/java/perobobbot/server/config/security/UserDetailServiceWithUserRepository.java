package perobobbot.server.config.security;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import perobobbot.data.domain.User;
import perobobbot.data.jpa.repository.UserRepository;

/**
 * @author Perococco
 */
@RequiredArgsConstructor
public class UserDetailServiceWithUserRepository implements UserDetailsService {

    @NonNull
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        final User user = userRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("Could not find user with name : " + login));
        return PerobobbotUserDetails.with(user);
    }
}

package perobobbot.server.config.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Perococco
 */
public class PerobobbotUserDetails extends User {

    public PerobobbotUserDetails(perobobbot.data.domain.User user) {
        super(user.getLogin(), user.getPassword(), user.transformedUserRoles(u -> new SimpleGrantedAuthority(u.getRoleName())));
    }

    public static UserDetails with(perobobbot.data.domain.User user) {
        return new PerobobbotUserDetails(user);
    }
}

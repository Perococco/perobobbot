package perobobbot.server.config.security;

import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import perobobbot.security.com.User;

/**
 * @author perococco
 */
public class PerobobbotUserDetails extends org.springframework.security.core.userdetails.User {

    public PerobobbotUserDetails(@NonNull User user) {
        super(user.getLogin(),
              user.getPassword(),
              !user.isDeactivated(),
              true,true,true,
              ExtractorOfGrantedAuthorities.extract(user));
    }

    public static UserDetails with(@NonNull User user) {
        return new PerobobbotUserDetails(user);
    }
}

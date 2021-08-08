package perobobbot.security.com;

import lombok.NonNull;
import org.springframework.security.core.GrantedAuthority;

import java.security.Principal;
import java.util.Collection;

public interface BotUser extends UsernameProvider {


    @NonNull Collection<? extends GrantedAuthority> getAuthorities();
}

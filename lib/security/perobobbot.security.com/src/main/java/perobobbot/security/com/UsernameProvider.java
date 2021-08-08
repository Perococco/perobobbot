package perobobbot.security.com;

import lombok.NonNull;

import java.security.Principal;

public interface UsernameProvider extends Principal {

    @NonNull String getUsername();
    
    default @NonNull String getName() {
        return getUsername();
    }

}

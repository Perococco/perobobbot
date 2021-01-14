package perobobbot.security.core;

import lombok.NonNull;
import perobobbot.security.com.User;

public interface UserProvider {

    /**
     * @param login the login of the user to search
     * @return the user with the provided login
     */
    @NonNull
    User getUserDetails(@NonNull String login);

    default @NonNull String getUserClaim(@NonNull String login) {
        return getUserDetails(login).getJwtClaim();
    }

}

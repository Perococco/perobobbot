package perobobbot.data.service;

import lombok.NonNull;
import perobobbot.data.com.User;

public interface UserProvider {

    /**
     * @param login the login of the user to search
     * @return the user with the provided login
     * @throws perobobbot.data.com.UnknownUser if no user with the provided login exists
     */
    @NonNull
    User getUser(@NonNull String login);

}

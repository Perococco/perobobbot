package perobobbot.data.service;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.data.com.CreateUserParameters;
import perobobbot.data.com.UpdateUserParameters;
import perobobbot.security.com.User;

public interface UserService {

    /**
     * @param login the login of the user to search
     * @return the user with the provided login
     * @throws perobobbot.data.com.UnknownUser if no user with the provided login exists
     */
    @NonNull User getUser(@NonNull String login);

    /**
     * @param parameters the parameters used to create a user
     * @return the created user
     */
    @NonNull User createUser(@NonNull CreateUserParameters parameters);

    @NonNull User updateUser(@NonNull String login, @NonNull UpdateUserParameters parameters);

    @NonNull ImmutableList<User> listAllUser();

}

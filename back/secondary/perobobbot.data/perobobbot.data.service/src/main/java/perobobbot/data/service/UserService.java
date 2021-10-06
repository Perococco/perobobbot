package perobobbot.data.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.data.com.CreateUserParameters;
import perobobbot.data.com.UpdateUserParameters;
import perobobbot.lang.Platform;
import perobobbot.security.com.BotUser;
import perobobbot.security.com.User;

import java.util.Optional;

public interface UserService {

    int VERSION = 1;

    /**
     * @param login the login of the user to search
     * @return the user with the provided login
     * @throws perobobbot.data.com.UnknownUser if no user with the provided login exists
     */
    @NonNull User getUser(@NonNull String login);

    /**
     * @param login the login of the user to search
     * @return the user with the provided login
     * @throws perobobbot.data.com.UnknownUser if no user with the provided login exists
     */
    @NonNull Optional<User> findUser(@NonNull String login);

    /**
     * @param login the login of the user to search
     * @return the user with the provided login
     * @throws perobobbot.data.com.UnknownUser if no user with the provided login exists
     */
    boolean doesUserExist(@NonNull String login);

    /**
     * @param parameters the parameters used to create a user
     * @return the created user
     */
    @NonNull User createUser(@NonNull CreateUserParameters parameters);

    @NonNull User updateUser(@NonNull String login, @NonNull UpdateUserParameters parameters);

    @NonNull ImmutableList<User> listAllUser();

    void changePassword(@NonNull String login, @NonNull String newPassword);

    @NonNull ImmutableSet<String> findLoginOfUsersAuthenticatedWithViewerId(@NonNull Platform platform, @NonNull String viewerId);

}

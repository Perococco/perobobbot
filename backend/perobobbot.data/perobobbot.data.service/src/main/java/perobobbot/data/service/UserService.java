package perobobbot.data.service;

import lombok.NonNull;
import perobobbot.data.com.CreateUserParameters;
import perobobbot.data.com.User;

public interface UserService extends UserProvider {

    @NonNull User getUser(@NonNull String login);

    @NonNull User createUser(@NonNull CreateUserParameters parameters);

}

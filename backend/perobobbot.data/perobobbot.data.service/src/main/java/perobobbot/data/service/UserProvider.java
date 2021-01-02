package perobobbot.data.service;

import lombok.NonNull;
import perobobbot.data.com.User;

public interface UserProvider {

    @NonNull
    User getUser(@NonNull String login);

}

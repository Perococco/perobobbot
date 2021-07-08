package perobobbot.lang;

import lombok.NonNull;

public interface UserAuthenticator {

    int VERSION = 1;

    boolean authenticate(@NonNull String login, @NonNull String secret);

    void clearAuthentication();
}

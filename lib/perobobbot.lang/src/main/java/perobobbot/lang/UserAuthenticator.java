package perobobbot.lang;

import lombok.NonNull;

import java.util.Optional;

public interface UserAuthenticator {

    int VERSION = 1;

    @NonNull Optional<String> authenticatedLogin();

    boolean authenticate(@NonNull String login, @NonNull String secret);

    void clearAuthentication();
}

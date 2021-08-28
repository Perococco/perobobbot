package perobobbot.oauth.tools;

import lombok.NonNull;

import java.util.Optional;

public interface OAuthCallHelper<T> {

    @NonNull T call() throws Throwable;
}

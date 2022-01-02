package perobobbot.oauth.tools;

import lombok.NonNull;

public interface OAuthCallHelper<T> {

    @NonNull T call() throws Throwable;
}

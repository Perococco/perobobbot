package perobobbot.oauth;

import lombok.NonNull;

public interface OAuthCall {

    Object call(@NonNull ApiToken apiToken) throws Throwable;

}

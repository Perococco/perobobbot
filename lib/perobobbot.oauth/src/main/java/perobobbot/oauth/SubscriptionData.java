package perobobbot.oauth;

import lombok.NonNull;

import java.net.URI;

public interface SubscriptionData {

    @NonNull String getState();

    @NonNull URI getOAuthRedirectURI();
}

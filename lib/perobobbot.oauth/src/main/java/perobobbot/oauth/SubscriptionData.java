package perobobbot.oauth;

import lombok.NonNull;
import lombok.Value;

import java.net.URI;

@Value
public class SubscriptionData {

    @NonNull String state;

    @NonNull URI oAuthRedirectURI;
}

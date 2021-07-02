package perobobbot.oauth;

import lombok.NonNull;
import lombok.Value;

import java.net.URI;

/**
 * Contains information to create the URI the user needs to get to authorize the application
 */
@Value
public class OauthSubscriptionData {

    @NonNull String state;

    @NonNull URI oAuthRedirectURI;
}

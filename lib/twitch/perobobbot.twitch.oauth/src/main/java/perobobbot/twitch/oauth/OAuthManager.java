package perobobbot.twitch.oauth;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;

public interface OAuthManager {

    @NonNull AppAccessToken getAppAccessToken(@NonNull ImmutableSet<Scope> scopes);

    @NonNull String getAuthorizationCodeFlowURI(@NonNull ImmutableSet<Scope> scopes);

}

package perobobbot.twitch.oauth;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;

public interface OAuthService {

    @NonNull AppAccessToken getAppAccessToken(@NonNull ImmutableSet<Scope> scopes);

}

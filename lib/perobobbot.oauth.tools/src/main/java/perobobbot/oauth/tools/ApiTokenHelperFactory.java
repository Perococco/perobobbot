package perobobbot.oauth.tools;

import lombok.NonNull;
import perobobbot.lang.Platform;
import perobobbot.oauth.OAuthRequirement;
import perobobbot.oauth.TokenIdentifier;

public interface ApiTokenHelperFactory {
    @NonNull ApiTokenHelper create(
            @NonNull Platform platform,
            @NonNull OAuthRequirement requirement,
            @NonNull TokenIdentifier tokenIdentifier
    );
}

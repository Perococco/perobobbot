package perobobbot.oauth.tools;

import lombok.NonNull;
import perobobbot.lang.Platform;
import perobobbot.lang.fp.Function2;
import perobobbot.oauth.OAuthRequirement;
import perobobbot.oauth.TokenIdentifier;

public interface ApiTokenHelperFactory {

    @NonNull ApiTokenHelper createWithToken(
            @NonNull Platform platform,
            @NonNull OAuthRequirement requirement,
            @NonNull TokenIdentifier tokenIdentifier
    );

    @NonNull ApiTokenHelper createWithoutToken(
            @NonNull Platform platform,
            @NonNull OAuthRequirement requirement
    );


    default Function2<Platform,OAuthRequirement,ApiTokenHelper> withToken(@NonNull TokenIdentifier tokenIdentifier) {
        return (platform, requirement) -> createWithToken(platform,requirement,tokenIdentifier);
    }
}

package perobobbot.oauth.tools;

import lombok.NonNull;
import perobobbot.data.service.BotService;
import perobobbot.data.service.ClientService;
import perobobbot.data.service.OAuthService;
import perobobbot.lang.Platform;
import perobobbot.oauth.ApiToken;
import perobobbot.oauth.OAuthRequirement;
import perobobbot.oauth.TokenIdentifier;
import perobobbot.oauth.tools._private.SimpleApiTokenHelper;

import java.util.Optional;

public interface ApiTokenHelper {

    /**
     * @return true if the token has been refreshed, false otherwise
     */
    boolean refreshToken();

    /**
     * Remove the token
     */
    void deleteToken();

    /**
     * @return the token obtained during
     */
    @NonNull Optional<ApiToken> getToken();


    static ApiTokenHelper simple(
            @NonNull ClientService clientService,
            @NonNull OAuthService oAuthService,
            @NonNull BotService botService,
            @NonNull Platform platform,
            @NonNull OAuthRequirement requirement
    ) {
        return new SimpleApiTokenHelper(
                clientService, oAuthService, botService, platform, requirement,null
        );
    }

    static ApiTokenHelper simple(
            @NonNull ClientService clientService,
            @NonNull OAuthService oAuthService,
            @NonNull BotService botService,
            @NonNull Platform platform,
            @NonNull OAuthRequirement requirement,
            @NonNull TokenIdentifier tokenIdentifier
    ) {
        return new SimpleApiTokenHelper(
                clientService, oAuthService, botService, platform, requirement, tokenIdentifier
        );
    }


}

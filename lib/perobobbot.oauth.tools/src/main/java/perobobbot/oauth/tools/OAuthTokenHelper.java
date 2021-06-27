package perobobbot.oauth.tools;

import lombok.NonNull;
import perobobbot.data.service.BotService;
import perobobbot.data.service.ClientService;
import perobobbot.data.service.OAuthService;
import perobobbot.lang.Platform;
import perobobbot.oauth.ScopeRequirements;
import perobobbot.oauth.tools._private.SimpleOAuthTokenHelper;

public interface OAuthTokenHelper {

    interface Factory {
        @NonNull OAuthTokenHelper create(@NonNull Platform platform);
    }

   boolean refreshToken();

   void removeTokenFromDb();

   void initializeOAuthContext(@NonNull ScopeRequirements scopeRequirements);

   static @NonNull OAuthTokenHelper simple(@NonNull ClientService clientService,
                                           @NonNull OAuthService oAuthService,
                                           @NonNull BotService botService,
                                           @NonNull Platform platform) {
       return new SimpleOAuthTokenHelper(clientService,oAuthService,botService,platform);
   }


}

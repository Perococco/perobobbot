package perobobbot.oauth.tools;

import lombok.NonNull;
import perobobbot.data.service.BotService;
import perobobbot.data.service.ClientService;
import perobobbot.data.service.OAuthService;
import perobobbot.lang.Platform;
import perobobbot.oauth.CallRequirements;
import perobobbot.oauth.tools._private.SimpleOAuthTokenHelper;

import java.lang.reflect.Method;

public interface OAuthTokenHelper {

    interface Factory {
        @NonNull OAuthTokenHelper create(@NonNull Platform platform, @NonNull CallRequirements.Factory callRequirementFactory);
    }

   boolean refreshToken();

   void removeTokenFromDb();

   void initializeOAuthContext(@NonNull Method method);

   static @NonNull OAuthTokenHelper simple(@NonNull ClientService clientService,
                                           @NonNull OAuthService oAuthService,
                                           @NonNull BotService botService,
                                           @NonNull Platform platform,
                                           @NonNull CallRequirements.Factory callRequirementFactory) {
       return new SimpleOAuthTokenHelper(clientService,oAuthService,botService,platform,callRequirementFactory);
   }


}

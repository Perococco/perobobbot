package perobobbot.twitch.client.webclient.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.reflect.MethodSignature;
import perobobbot.data.service.BotService;
import perobobbot.data.service.EventService;
import perobobbot.data.service.OAuthService;
import perobobbot.lang.Platform;
import perobobbot.lang.token.DecryptedUserTokenView;
import perobobbot.oauth.OAuthContextHolder;

@RequiredArgsConstructor
public class OAuthTokenInitializer {

    private final @NonNull
    @EventService
    OAuthService oAuthService;
    private final @NonNull
    @EventService
    BotService botService;

    private final @NonNull Platform platform;


    protected void initialize(@NonNull MethodSignature methodSignature) {
        CallRequirements.createFromSignature(methodSignature)
                        .ifPresent(callRequirements -> {
                            switch (callRequirements.getTokenType()) {
                                case CLIENT_TOKEN -> retrieveClientToken();
                                case USER_TOKEN -> retrieveUserToken(callRequirements);
                            }
                        });
    }

    private void retrieveClientToken() {
        oAuthService.findClientToken(platform).ifPresent(
                token -> OAuthContextHolder.getContext().setClientToken(token.getClientToken()));
    }


    private void retrieveUserToken(@NonNull CallRequirements callRequirements) {
        final var tokenGetter = UserTokenViewGetter.createTokenGetter(oAuthService, platform, callRequirements);
        OAuthContextHolder.getContext()
                          .getTokenIdentifier()
                          .flatMap(new LoginGetter(botService).asFunction())
                          .flatMap(tokenGetter)
                          .map(DecryptedUserTokenView::getUserToken)
                          .ifPresent(OAuthContextHolder.getContext()::setUserToken);
    }

}

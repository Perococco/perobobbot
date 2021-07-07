package perobobbot.server.oauth;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import perobobbot.data.service.BotService;
import perobobbot.data.service.ClientService;
import perobobbot.data.service.EventService;
import perobobbot.data.service.OAuthService;
import perobobbot.lang.Platform;
import perobobbot.oauth.OAuthRequirement;
import perobobbot.oauth.TokenIdentifier;
import perobobbot.oauth.tools.ApiTokenHelper;
import perobobbot.oauth.tools.ApiTokenHelperFactory;

@Component
@RequiredArgsConstructor
public class OAuthTokeInitializerFactory implements ApiTokenHelperFactory {

    private final @NonNull @EventService
    ClientService clientService;

    private final @NonNull @EventService
    OAuthService oAuthService;

    private final @NonNull @EventService
    BotService botService;

    @Override
    public @NonNull ApiTokenHelper create(@NonNull Platform platform,
                                          @NonNull OAuthRequirement requirement,
                                          @NonNull TokenIdentifier tokenIdentifier) {
        return ApiTokenHelper.simple(clientService, oAuthService, botService, platform, requirement, tokenIdentifier);
    }
}

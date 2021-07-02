package perobobbot.server.oauth;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import perobobbot.data.service.BotService;
import perobobbot.data.service.ClientService;
import perobobbot.data.service.EventService;
import perobobbot.data.service.OAuthService;
import perobobbot.lang.Platform;
import perobobbot.oauth.tools.OAuthTokenHelper;

@Component
@RequiredArgsConstructor
public class OAuthTokeInitializerFactory implements OAuthTokenHelper.Factory {

    private final @NonNull @EventService
    ClientService clientService;

    private final @NonNull @EventService
    OAuthService oAuthService;

    private final @NonNull @EventService
    BotService botService;

    @Override
    public @NonNull OAuthTokenHelper create(@NonNull Platform platform) {
        return OAuthTokenHelper.simple(clientService, oAuthService, botService, platform);
    }
}

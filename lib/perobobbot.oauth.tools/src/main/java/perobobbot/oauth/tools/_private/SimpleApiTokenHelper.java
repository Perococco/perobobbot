package perobobbot.oauth.tools._private;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import perobobbot.data.service.BotService;
import perobobbot.data.service.ClientService;
import perobobbot.data.service.OAuthService;
import perobobbot.lang.Platform;
import perobobbot.lang.client.SafeClient;
import perobobbot.lang.token.DecryptedBotToken;
import perobobbot.lang.token.DecryptedClientTokenView;
import perobobbot.lang.token.DecryptedUserTokenView;
import perobobbot.oauth.*;
import perobobbot.oauth.tools.ApiTokenHelper;
import perobobbot.oauth.tools.BasicOAuthService;
import perobobbot.oauth.tools.UserTokenViewGetter;

import java.util.Optional;

@RequiredArgsConstructor
@Log4j2
public class SimpleApiTokenHelper implements ApiTokenHelper {


    private final @NonNull ClientService clientService;
    private final @NonNull OAuthService oAuthService;
    private final @NonNull BotService botService;


    private final @NonNull Platform platform;
    private final @NonNull OAuthRequirement requirement;
    private final TokenIdentifier tokenIdentifier;

    private SafeClient safeClient;
    private DecryptedClientTokenView clientTokenView;
    private DecryptedUserTokenView userTokenView;
    private DecryptedBotToken botToken;

    private boolean initialized = false;

    @Override
    public boolean refreshToken() {
        this.initialize();
        if (clientTokenView != null || botToken != null) {
            clientTokenView = oAuthService.authenticateClient(platform);
        }
        if (userTokenView != null) {
            userTokenView = oAuthService.refreshUserToken(userTokenView.getId());
        }
        return true;
    }

    @Override
    public void deleteToken() {
        this.initialize();
        if (clientTokenView != null || botToken != null) {
            oAuthService.deleteClientToken(clientTokenView.getId());
        }
        if (userTokenView != null) {
            oAuthService.deleteUserToken(userTokenView.getId());
        }
        userTokenView = null;
        clientTokenView = null;
        botToken = null;
    }

    @Override
    public @NonNull Optional<ApiToken> getToken() {
        this.initialize();
        final ApiToken apiToken;
        if (userTokenView != null) {
            apiToken = new UserApiToken(userTokenView.getUserId(), safeClient.getClientId(), userTokenView.getUserToken().getAccessToken());
        } else if (clientTokenView != null) {
            apiToken = new ClientApiToken(safeClient.getClientId(), clientTokenView.getToken().getAccessToken());
        } else if (botToken != null) {
            apiToken = new BotApiToken(botToken.getClientId(), botToken.getBotToken());
        } else {
            apiToken = null;
        }
        return Optional.ofNullable(apiToken);
    }

    private void initialize() {
        if (initialized) {
            return;
        }
        requirement.getClientOAuth().ifPresent(this::retrieveClientToken);
        requirement.getUserOAuth().ifPresent(this::retrieveUserToken);
        requirement.getBotOAuth().ifPresent(this::retrieveBotToken);
        safeClient = retrieveClient();
        this.initialized = true;
    }


    private @NonNull SafeClient retrieveClient() {
        return Optional.ofNullable(clientTokenView)
                       .map(DecryptedClientTokenView::getClient)
                       .orElseGet(() -> clientService.getSafeClient(platform));
    }


    private void retrieveClientToken(@NonNull ClientOAuth clientOAuth) {
        clientTokenView = oAuthService.findOrAuthenticateClientToken(platform);
        LOG.debug(Markers.OAUTH_MARKER, "Use client token {}", clientTokenView.getId());
    }

    private void retrieveBotToken(@NonNull BotOAuth botOAuth) {
        botToken = oAuthService.getBotToken(platform);
        LOG.debug(Markers.OAUTH_MARKER, "Use bot token for platform {}", platform);
    }


    private void retrieveUserToken(@NonNull UserOAuth userOAuth) {
        if (tokenIdentifier == null) {
            LOG.warn(Markers.OAUTH_MARKER, "TokenIdentifier missing for UserOAuth");
            return;
        }
        final var tokenGetter = UserTokenViewGetter.createTokenGetter(platform, userOAuth);
        final var userToken = tokenIdentifier.accept(new BasicOAuthServiceGetter(oAuthService))
                                             .flatMap(tokenGetter)
                                             .orElse(null);


        if (userToken != null) {
            LOG.debug(Markers.OAUTH_MARKER, "Use user token {}", userToken.getId());
            this.userTokenView = userToken;
        } else {
            LOG.debug(Markers.OAUTH_MARKER, "No user token found");
        }
    }

    @RequiredArgsConstructor
    private class BasicOAuthServiceGetter implements TokenIdentifier.Visitor<Optional<BasicOAuthService>> {

        private final OAuthService oAuthService;

        @Override
        public @NonNull Optional<BasicOAuthService> visit(@NonNull ChatTokenIdentifier tokenIdentifier) {
            if (tokenIdentifier.getPlatform() != platform) {
                return Optional.empty();
            }
            return botService.findLoginOfBotOwner(tokenIdentifier.getBotId())
                             .map(login -> new LoginBaseBasicOauthService(oAuthService, login));
        }

        @Override
        public @NonNull Optional<BasicOAuthService> visit(@NonNull LoginTokenIdentifier tokenIdentifier) {
            return Optional.of(new LoginBaseBasicOauthService(oAuthService, tokenIdentifier.getLogin()));
        }

        @Override
        public @NonNull Optional<BasicOAuthService> visit(@NonNull BroadcasterIdentifier tokenIdentifier) {
            if (tokenIdentifier.getPlatform() != platform) {
                return Optional.empty();
            }

            return Optional.of(new BroadcasterBaseBasicOauthService(oAuthService, tokenIdentifier.getBroadcasterId()));
        }
    }
}

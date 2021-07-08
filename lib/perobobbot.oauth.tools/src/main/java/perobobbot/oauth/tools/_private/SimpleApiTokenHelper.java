package perobobbot.oauth.tools._private;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import perobobbot.data.service.BotService;
import perobobbot.data.service.ClientService;
import perobobbot.data.service.OAuthService;
import perobobbot.lang.Platform;
import perobobbot.lang.SafeClient;
import perobobbot.lang.token.DecryptedClientTokenView;
import perobobbot.lang.token.DecryptedUserTokenView;
import perobobbot.oauth.*;
import perobobbot.oauth.tools.ApiTokenHelper;
import perobobbot.oauth.tools.LoginGetter;
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

    @Override
    public void initialize() {
        requirement.getClientOAuth().ifPresent(this::retrieveClientToken);
        requirement.getUserOAuth().ifPresent(this::retrieveUserToken);
        safeClient = retrieveClient();
    }

    @Override
    public boolean refreshToken() {
        if (clientTokenView != null) {
            clientTokenView = oAuthService.authenticateClient(platform);
        }
        if (userTokenView != null) {
            userTokenView = oAuthService.refreshUserToken(userTokenView.getId());
        }
        return true;
    }

    @Override
    public void deleteToken() {
        if (clientTokenView != null) {
            oAuthService.deleteClientToken(clientTokenView.getId());
        }
        if (userTokenView != null) {
            oAuthService.deleteUserToken(userTokenView.getId());
        }
        userTokenView = null;
        clientTokenView = null;
    }

    @Override
    public @NonNull Optional<ApiToken> getToken() {
        final ApiToken apiToken;
        if (userTokenView != null) {
            apiToken = new UserApiToken(userTokenView.getViewerId(), safeClient.getClientId(), userTokenView.getUserToken().getAccessToken());
        } else if (clientTokenView != null) {
            apiToken = new ClientApiToken(safeClient.getClientId(), clientTokenView.getToken().getAccessToken());
        } else {
            apiToken = null;
        }
        return Optional.ofNullable(apiToken);
    }


    private @NonNull SafeClient retrieveClient() {
        return Optional.of(clientTokenView)
                       .map(DecryptedClientTokenView::getClient)
                       .orElseGet(() -> clientService.getSafeClient(platform));
    }


    private void retrieveClientToken(@NonNull ClientOAuth clientOAuth) {
        clientTokenView = oAuthService.findOrAuthenticateClientToken(platform);
        LOG.debug(Markers.OAUTH_MARKER, "Use client token {}", clientTokenView.getId());
    }


    private void retrieveUserToken(@NonNull UserOAuth userOAuth) {
        if (tokenIdentifier == null) {
            LOG.warn(Markers.OAUTH_MARKER, "TokenIdentifier missing for UserOAuth");
            return;
        }
        final var tokenGetter = UserTokenViewGetter.createTokenGetter(oAuthService, platform, userOAuth);
        final var userToken = new LoginGetter(botService).f(tokenIdentifier)
                                                         .flatMap(tokenGetter)
                                                         .orElse(null);

        if (userToken != null) {
            LOG.debug(Markers.OAUTH_MARKER, "Use user token {}", userToken.getId());
            this.userTokenView = userToken;
        } else {
            LOG.debug(Markers.OAUTH_MARKER, "No user token found");
        }
    }
}

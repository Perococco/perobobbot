package perobobbot.oauth.tools._private;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import perobobbot.data.service.BotService;
import perobobbot.data.service.ClientService;
import perobobbot.data.service.OAuthService;
import perobobbot.lang.Platform;
import perobobbot.lang.TokenType;
import perobobbot.lang.fp.Consumer1;
import perobobbot.lang.token.DecryptedClientTokenView;
import perobobbot.lang.token.DecryptedUserTokenView;
import perobobbot.lang.token.TokenView;
import perobobbot.oauth.CallRequirements;
import perobobbot.oauth.Markers;
import perobobbot.oauth.OAuthContext;
import perobobbot.oauth.OAuthContextHolder;
import perobobbot.oauth.tools.LoginGetter;
import perobobbot.oauth.tools.OAuthTokenHelper;
import perobobbot.oauth.tools.UserTokenViewGetter;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

@Log4j2
@RequiredArgsConstructor
public class SimpleOAuthTokenHelper implements OAuthTokenHelper {

    public interface Factory {
        @NonNull SimpleOAuthTokenHelper create(@NonNull Platform platform, @NonNull CallRequirements.Factory callRequirementFactory);
    }

    private final @NonNull ClientService clientService;
    private final @NonNull OAuthService oAuthService;
    private final @NonNull BotService botService;

    private final @NonNull Platform platform;
    private final @NonNull CallRequirements.Factory callRequirementFactory;


    @Override
    public boolean refreshToken() {
        final var context = OAuthContextHolder.getContext();
        final var tokenType = context.getTokenType();

        if (tokenType == TokenType.CLIENT_TOKEN) {
            this.setupNewClientToken(context);
        } else {
            context.getUserToken().ifPresent(token -> this.setupRefreshedUserToken(context, token));
        }
        return true;
    }

    @Override
    public void removeTokenFromDb() {
        final var context = OAuthContextHolder.getContext();
        final var tokenType = context.getTokenType();

        switch (tokenType) {
            case CLIENT_TOKEN -> performRemoval(context::getClientToken, oAuthService::deleteClientToken);
            case USER_TOKEN -> performRemoval(context::getUserToken, oAuthService::deleteUserToken);
        }

    }

    @Override
    public void initializeOAuthContext(@NonNull Method method) {
        final var callRequirements = this.callRequirementFactory.create(method).orElse(null);
        if (callRequirements == null) {
            return;
        }

        LOG.info(Markers.OAUTH_MARKER, "Setup oauth context for {}", callRequirements);

        final var tokenType = callRequirements.getTokenType();
        this.addCallRequirementsToOAuthContext(callRequirements);

        switch (tokenType) {
            case CLIENT_TOKEN -> setupClientToken();
            case USER_TOKEN -> setupUserToken(callRequirements);
        }

        this.retrieveClientInformation();
    }

    private void performRemoval(@NonNull Supplier<? extends Optional<? extends TokenView<?>>> s, @NonNull Consumer1<? super UUID> deleter) {
        s.get().map(TokenView::getId).ifPresent(deleter);
    }

    private void addCallRequirementsToOAuthContext(@NonNull CallRequirements callRequirements) {
        OAuthContextHolder.getContext().setCallRequirements(callRequirements);
    }

    private void setupClientToken() {
        final var clientToken = oAuthService.findOrAuthenticateClientToken(platform);
        LOG.info(Markers.OAUTH_MARKER, "Use client token {}", clientToken.getId());
        OAuthContextHolder.getContext().setClientToken(clientToken);
    }

    private void setupUserToken(@NonNull CallRequirements callRequirements) {
        final var tokenGetter = UserTokenViewGetter.createTokenGetter(oAuthService, platform, callRequirements);
        final var context = OAuthContextHolder.getContext();

        final var userToken = context
                .getTokenIdentifier()
                .flatMap(new LoginGetter(botService))
                .flatMap(tokenGetter).orElse(null);

        if (userToken != null) {
            LOG.info(Markers.OAUTH_MARKER, "Use user token {}", userToken.getId());
            context.setUserToken(userToken);
        } else {
            LOG.info(Markers.OAUTH_MARKER, "No user token found");
        }
    }

    private void retrieveClientInformation() {
        final var context = OAuthContextHolder.getContext();

        final var client = context.getClientToken()
                                  .map(DecryptedClientTokenView::getClient)
                                  .orElseGet(() -> clientService.getSafeClient(platform));

        context.setClient(client);
    }


    public void setupNewClientToken(@NonNull OAuthContext context) {
        final var token = oAuthService.authenticateClient(platform);
        context.setClientToken(token);
    }

    public void setupRefreshedUserToken(@NonNull OAuthContext context, @NonNull DecryptedUserTokenView token) {
        final DecryptedUserTokenView refreshedToken = oAuthService.refreshUserToken(token);
        context.setUserToken(refreshedToken);
    }


}

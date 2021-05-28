package perobobbot.data.security.service;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import perobobbot.data.service.EventService;
import perobobbot.data.service.OAuthService;
import perobobbot.data.service.SecuredService;
import perobobbot.lang.Client;
import perobobbot.lang.Platform;
import perobobbot.lang.PluginService;
import perobobbot.lang.Scope;
import perobobbot.lang.token.DecryptedClientTokenView;
import perobobbot.lang.token.DecryptedUserTokenView;
import perobobbot.oauth.UserOAuthInfo;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@SecuredService
@PluginService(type = OAuthService.class, apiVersion = OAuthService.VERSION)
public class SecuredOAuthService implements OAuthService {

    private final @EventService OAuthService delegate;


    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public @NonNull ImmutableSet<Client> getClients() {
        return delegate.getClients();
    }

    @Override
    @NonNull
    @PreAuthorize("hasRole('ADMIN')")
    public Optional<DecryptedClientTokenView> findClientToken(@NonNull UUID clientId) {
        return delegate.findClientToken(clientId);
    }

    @Override
    @NonNull
    @PreAuthorize("hasRole('ADMIN')")
    public Optional<DecryptedClientTokenView> findClientToken(@NonNull Platform platform) {
        return delegate.findClientToken(platform);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public @NonNull DecryptedClientTokenView authenticateClient(@NonNull UUID clientId) {
        return delegate.authenticateClient(clientId);
    }


    @Override
    @PreAuthorize("hasRole('ADMIN') || authentication.name == #login")
    public @NonNull ImmutableSet<DecryptedUserTokenView> getAllUserTokens(@NonNull String login) {
        return delegate.getAllUserTokens(login);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') || authentication.name == #login")
    public @NonNull Optional<DecryptedUserTokenView> findUserToken(@NonNull String login, @NonNull Platform platform) {
        return delegate.findUserToken(login,platform);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') || authentication.name == #login")
    public @NonNull Optional<DecryptedUserTokenView> findUserToken(@NonNull String login, @NonNull Platform platform, @NonNull Scope requiredScope) {
        return delegate.findUserToken(login,platform,requiredScope);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') || authentication.name == #login")
    public @NonNull UserOAuthInfo authenticateUser(@NonNull String login, @NonNull ImmutableSet<? extends Scope> scopes, @NonNull Platform platform) {
        return delegate.authenticateUser(login,scopes,platform);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') || hasPermission('UserTokenEntity','READ')")
    public @NonNull Optional<DecryptedUserTokenView> findUserToken(@NonNull UUID tokenId) {
        return delegate.findUserToken(tokenId);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') || hasPermission('UserTokenEntity','READ')")
    public @NonNull DecryptedUserTokenView getUserToken(@NonNull UUID tokenId) {
        return delegate.getUserToken(tokenId);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') || hasPermission('UserTokenEntity','DELETE')")
    public void deleteUserToken(@NonNull UUID tokenId) {
        delegate.deleteUserToken(tokenId);
    }
}

package perobobbot.data.security.service;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import perobobbot.data.com.AuthenticateUserParameter;
import perobobbot.data.service.EventService;
import perobobbot.data.service.SecuredService;
import perobobbot.data.service.OAuthService;
import perobobbot.lang.Client;
import perobobbot.lang.Platform;
import perobobbot.lang.PluginService;
import perobobbot.lang.token.DecryptedClientTokenView;
import perobobbot.lang.token.DecryptedUserTokenView;

import java.util.Optional;
import java.util.UUID;

@Service
@SecuredService
@RequiredArgsConstructor
@PluginService(type = OAuthService.class, apiVersion = OAuthService.VERSION)
public class SecuredOAuthService implements OAuthService {

    private final @EventService
    OAuthService delegate;


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
    public Optional<DecryptedClientTokenView> findClientToken(@NonNull Platform platform, @NonNull String clientId) {
        return delegate.findClientToken(platform, clientId);
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
    @NonNull
    @PreAuthorize("hasRole('ADMIN') || authentication.name == #login")
    public Optional<DecryptedUserTokenView> findUserToken(@NonNull String login, @NonNull UUID viewerIdentityId) {
        return delegate.findUserToken(login, viewerIdentityId);
    }

    @Override
    @NonNull
    @PreAuthorize("hasRole('ADMIN') || authentication.name == #login")
    public Optional<DecryptedUserTokenView> findUserToken(@NonNull String login, @NonNull Platform platform, @NonNull String viewerId) {
        return delegate.findUserToken(login, platform, viewerId);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') || authentication.name == #parameter.login")
    public @NonNull DecryptedUserTokenView authenticateUser(@NonNull AuthenticateUserParameter parameter, @NonNull UUID clientId) {
        return delegate.authenticateUser(parameter,clientId);
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

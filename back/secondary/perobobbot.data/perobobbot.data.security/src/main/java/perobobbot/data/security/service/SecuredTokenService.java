package perobobbot.data.security.service;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import perobobbot.data.service.EventService;
import perobobbot.data.service.SecuredService;
import perobobbot.data.service.TokenService;
import perobobbot.lang.Platform;
import perobobbot.lang.token.DecryptedClientToken;
import perobobbot.lang.PluginService;
import perobobbot.lang.token.DecryptedClientTokenView;
import perobobbot.lang.token.DecryptedUserToken;
import perobobbot.lang.token.DecryptedUserTokenView;

import java.util.Optional;
import java.util.UUID;

@Service
@SecuredService
@RequiredArgsConstructor
@PluginService(type = TokenService.class, apiVersion = TokenService.VERSION)
public class SecuredTokenService implements TokenService {

    private final @EventService TokenService delegate;


    @Override
    @PreAuthorize("hasRole('ADMIN') || hasPermission(#ownerLogin, 'UserTokenEntity','READ')")
    public @NonNull ImmutableList<DecryptedUserTokenView> getUserTokens(@NonNull String ownerLogin) {
        return delegate.getUserTokens(ownerLogin);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') || hasPermission(#tokenId, 'UserTokenEntity','READ_TOKEN')")
    public @NonNull Optional<DecryptedUserTokenView> findUserToken(@NonNull UUID tokenId) {
        return delegate.findUserToken(tokenId);
    }


    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void saveClientToken(@NonNull UUID clientId, @NonNull DecryptedClientToken token) {
        delegate.saveClientToken(clientId,token);
    }


    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteClientToken(@NonNull UUID tokenId) {
        delegate.deleteClientToken(tokenId);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')|| hasPermission(#tokenId, 'UserTokenEntity','DELETE_TOKEN')")
    public void deleteUserToken(@NonNull UUID tokenId) {
        delegate.deleteUserToken(tokenId);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')|| hasPermission(#ownerLogin, 'UserTokenEntity','CREATE')")
    public void saveUserToken(@NonNull String ownerLogin, @NonNull UUID viewerIdentityId, @NonNull DecryptedUserToken userToken) {
        delegate.saveUserToken(ownerLogin,viewerIdentityId,userToken);
    }
}

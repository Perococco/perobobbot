package perobobbot.data.security.service;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import perobobbot.data.com.DataCredentialInfo;
import perobobbot.data.service.BotService;
import perobobbot.data.service.CredentialService;
import perobobbot.data.service.EventService;
import perobobbot.data.service.SecuredService;
import perobobbot.lang.Credential;
import perobobbot.lang.Platform;
import perobobbot.lang.PluginService;

import java.util.Optional;
import java.util.UUID;

@Service
@SecuredService
@RequiredArgsConstructor
@PluginService(type = CredentialService.class, apiVersion = CredentialService.VERSION)
public class SecuredCredentialService implements CredentialService {

    private final @EventService CredentialService delegate;

    @Override
    @PreAuthorize("hasRole('ADMIN') || authentication.name == #login")
    public @NonNull ImmutableList<DataCredentialInfo> getUserCredentials(@NonNull String login) {
        return delegate.getUserCredentials(login);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') || hasPermission(#id, 'CredentialEntity','UPDATE')")
    public @NonNull DataCredentialInfo updateCredential(@NonNull UUID id, @NonNull Credential credential) {
        return delegate.updateCredential(id, credential);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') || hasPermission(#id, 'CredentialEntity','READ')")
    public @NonNull Optional<DataCredentialInfo> findCredential(@NonNull UUID id) {
        return delegate.findCredential(id);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') || hasPermission(#id, 'CredentialEntity','DELETE')")
    public void deleteCredential(@NonNull UUID id) {
        delegate.deleteCredential(id);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') || authentication.name == #login")
    public @NonNull DataCredentialInfo createCredential(@NonNull String login, @NonNull Platform platform) {
        return delegate.createCredential(login,platform);
    }
}

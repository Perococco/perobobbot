package perobobbot.data.security.service;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import perobobbot.data.com.CreateUserParameters;
import perobobbot.data.com.DataCredentialInfo;
import perobobbot.data.com.User;
import perobobbot.data.service.CredentialService;
import perobobbot.data.service.EventService;
import perobobbot.data.service.SecuredService;
import perobobbot.data.service.UserService;
import perobobbot.lang.Credential;
import perobobbot.lang.Platform;

import java.util.Optional;
import java.util.UUID;

@Service
@SecuredService
@RequiredArgsConstructor
public class SecuredCredentialService implements CredentialService {

    private final @EventService CredentialService delegate;

    @Override
    @PreAuthorize("hasRole('ADMIN') || authentication.name == #login")
    public @NonNull ImmutableList<DataCredentialInfo> getCredentials(@NonNull String login) {
        return delegate.getCredentials(login);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') || authentication.name == #login")
    public @NonNull Optional<DataCredentialInfo> getCredential(@NonNull String login, @NonNull Platform platform) {
        return delegate.getCredential(login, platform);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') || authentication.name == #login")
    public @NonNull DataCredentialInfo updateCredential(@NonNull String login, @NonNull Platform platform, @NonNull Credential credential) {
        return delegate.updateCredential(login, platform, credential);
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
}

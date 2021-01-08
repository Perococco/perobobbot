package perobobbot.data.service;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.data.com.CreateUserParameters;
import perobobbot.data.com.DataCredentialInfo;
import perobobbot.data.com.UnknownCredential;
import perobobbot.data.com.User;
import perobobbot.lang.Credential;
import perobobbot.lang.Platform;

import java.util.Optional;
import java.util.UUID;

public interface CredentialService {

    @NonNull ImmutableList<DataCredentialInfo> getCredentials(@NonNull String login);

    @NonNull Optional<DataCredentialInfo> getCredential(@NonNull String login, @NonNull Platform platform);

    @NonNull DataCredentialInfo updateCredential(@NonNull String login, @NonNull Platform platform, @NonNull Credential credential);

    @NonNull Optional<DataCredentialInfo> findCredential(@NonNull UUID uuid);

    default @NonNull DataCredentialInfo getCredential(@NonNull UUID uuid) {
        return findCredential(uuid).orElseThrow(() -> new UnknownCredential(uuid));
    }

    void deleteCredential(@NonNull UUID id);
}

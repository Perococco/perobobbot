package perobobbot.rest.client;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.rest.com.RestCredentialInfo;

import java.util.UUID;

public interface CredentialClient {

    @NonNull ImmutableList<RestCredentialInfo> getCredentials();

    @NonNull RestCredentialInfo getCredential(@NonNull UUID id);

    void deleteCredential(@NonNull UUID id);

}

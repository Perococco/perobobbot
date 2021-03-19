package perobobbot.rest.client;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.rest.com.RestCredentialInfo;

import java.util.UUID;
import java.util.concurrent.CompletionStage;

public interface CredentialClient {

    @NonNull CompletionStage<ImmutableList<RestCredentialInfo>> getCredentials();

    @NonNull CompletionStage<RestCredentialInfo> getCredential(@NonNull UUID id);

    @NonNull CompletionStage<?> deleteCredential(@NonNull UUID id);

}

package perococco.perobobbot.rest.client.template;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.Todo;
import perobobbot.rest.client.CredentialClient;
import perobobbot.rest.com.RestCredentialInfo;

import java.util.UUID;
import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
public class RestTemplateCredentialClient implements CredentialClient {

    @NonNull
    private final AsyncRestOperations restOperations;

    @Override
    public @NonNull CompletionStage<ImmutableList<RestCredentialInfo>> getCredentials() {
        return Todo.TODO();
    }

    @Override
    public @NonNull CompletionStage<RestCredentialInfo> getCredential(@NonNull UUID id) {
        return Todo.TODO();
    }

    @Override
    public @NonNull CompletionStage<?> deleteCredential(@NonNull UUID id) {
        return Todo.TODO();
    }
}

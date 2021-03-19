package perococco.perobobbot.rest.client.template;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.data.com.Extension;
import perobobbot.lang.Todo;
import perobobbot.rest.client.ExtensionClient;

import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
public class RestTemplateExtensionClient implements ExtensionClient {

    private final @NonNull AsyncRestOperations restOperations;

    @Override
    public @NonNull CompletionStage<ImmutableList<Extension>> listExtensions() {
        return Todo.TODO();
    }
}

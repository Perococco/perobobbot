package perococco.perobobbot.rest.client.template;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.Todo;
import perobobbot.rest.client.I18nClient;

import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
public class RestTemplateI18nClient implements I18nClient {

    private final @NonNull AsyncRestOperations restOperations;

    @Override
    public @NonNull CompletionStage<ImmutableList<String>> getAvailableLanguageTags() {
        return Todo.TODO();
    }

    @Override
    public @NonNull CompletionStage<ImmutableMap<String, String>> getDictionary(@NonNull String languageTag) {
        return Todo.TODO();
    }
}

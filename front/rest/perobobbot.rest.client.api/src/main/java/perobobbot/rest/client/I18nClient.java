package perobobbot.rest.client;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.NonNull;

import java.util.concurrent.CompletionStage;

public interface I18nClient {

    @NonNull CompletionStage<ImmutableList<String>> getAvailableLanguageTags();


    @NonNull CompletionStage<ImmutableMap<String,String>> getDictionary(@NonNull String languageTag);
}

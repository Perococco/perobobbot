package perobobbot.rest.client;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.NonNull;

public interface I18nClient {

    @NonNull ImmutableList<String> getAvailableLanguageTags();


    @NonNull ImmutableMap<String,String> getDictionary(@NonNull String languageTag);
}

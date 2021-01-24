package perobobbot.rest.controller;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import perobobbot.i18n.Dictionaries;
import perobobbot.lang.ListTool;
import perococco.i18n.DictionaryProvider;

import java.util.Locale;

@RestController
@RequestMapping("/api/dictionaries")
@RequiredArgsConstructor
public class I18nController {

    @NonNull
    private final DictionaryProvider dictionaryProvider = Dictionaries.INSTANCE;


    @GetMapping("")
    public @NonNull ImmutableList<String> getAvailableLanguageTags() {
        return ListTool.map(dictionaryProvider.getAvailableLocales(), Locale::toLanguageTag);
    }


    @GetMapping("/{languageTag}")
    public @NonNull ImmutableMap<String,String> getDictionary(@PathVariable String languageTag) {
        final var locale = Locale.forLanguageTag(languageTag);
        final var dictionary = dictionaryProvider.getDictionary(locale);
        if (dictionary.isEmpty()) {
            return dictionaryProvider.getDictionary(Locale.ENGLISH).getValues();
        }
        return dictionary.getValues();
    }
}

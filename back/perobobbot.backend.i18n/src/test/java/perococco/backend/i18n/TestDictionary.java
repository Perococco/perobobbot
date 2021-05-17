package perococco.backend.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import perobobbot.backend.i18n.Dictionaries;

import java.util.Locale;
import java.util.stream.Stream;

public class TestDictionary {

    public static Stream<Locale> locales() {
        return Stream.of(Locale.ENGLISH,Locale.FRANCE,Locale.FRENCH);
    }

    @ParameterizedTest
    @MethodSource("locales")
    public void dictionaryShouldNotBeEmpty(Locale locale) {
        final var dict = Dictionaries.INSTANCE.getDictionary(locale);
        Assertions.assertFalse(dict.isEmpty());
    }
}

package perobobbot.data.com;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.TypeScript;

import java.util.Optional;

@TypeScript
@Value
public class UpdateUserParameters {

    String languageTag;

    public @NonNull Optional<String> getLanguageTag() {
        return Optional.ofNullable(languageTag);
    }
}

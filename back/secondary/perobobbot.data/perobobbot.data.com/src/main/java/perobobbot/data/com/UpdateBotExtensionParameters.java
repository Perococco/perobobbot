package perobobbot.data.com;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.TypeScript;

import java.util.Optional;

@Value
@TypeScript
public class UpdateBotExtensionParameters {

    Boolean enabled;

    @JsonIgnore
    public @NonNull Optional<Boolean> getEnabledAsOptional() {
        return Optional.ofNullable(enabled);
    }
}

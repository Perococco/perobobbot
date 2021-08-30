package perobobbot.data.com;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.TypeScript;

import java.util.Optional;

@Value
@TypeScript
public class UpdateExtensionParameters {

    Boolean activated;

    @JsonIgnore
    public @NonNull Optional<Boolean> getActivatedAsOptional() {
        return Optional.ofNullable(activated);
    }
}

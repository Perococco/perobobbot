package perococco.command;

import lombok.NonNull;
import perobobbot.command.MessageErrorResolver;
import perobobbot.lang.PerobobbotException;

import java.util.Optional;

public class BasicMessageErrorResolver implements MessageErrorResolver {

    @Override
    public @NonNull Optional<String> resolve(@NonNull Throwable t) {
        if (t instanceof PerobobbotException) {
            return Optional.of("an error occurred");
        }
        return Optional.empty();
    }
}

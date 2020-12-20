package perobobbot.command;

import lombok.NonNull;

import java.util.Optional;

public interface MessageErrorResolver {

    @NonNull Optional<String> resolve(@NonNull Throwable t);
}

package perobobbot.command;

import lombok.NonNull;
import perobobbot.lang.Platform;

public interface CommandControllerBuilder {

    @NonNull CommandControllerBuilder setCommandPrefix(char prefix);

    @NonNull CommandControllerBuilder setCommandRegistry(@NonNull CommandRegistry commandRegistry);

    @NonNull CommandControllerBuilder setCommandPrefix(@NonNull Platform platform, char prefix);

    @NonNull CommandController build();
}

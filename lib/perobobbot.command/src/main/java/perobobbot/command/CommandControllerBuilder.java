package perobobbot.command;

import lombok.NonNull;
import perobobbot.chat.core.IO;
import perobobbot.lang.Platform;

public interface CommandControllerBuilder {

    @NonNull CommandControllerBuilder commandRegistry(@NonNull CommandRegistry commandRegistry);

    @NonNull CommandControllerBuilder setCommandPrefix(char prefix);

    @NonNull CommandControllerBuilder setCommandPrefix(@NonNull Platform platform, char prefix);

    @NonNull CommandController build();

}

package perobobbot.command;

import lombok.NonNull;
import perobobbot.lang.MessageHandler;
import perococco.command.PeroCommandControllerBuilder;

public interface CommandController extends MessageHandler {

    static @NonNull CommandControllerBuilder builder() {
        return new PeroCommandControllerBuilder();
    }

}

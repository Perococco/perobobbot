package perobobbot.command;

import lombok.NonNull;
import perobobbot.chat.core.IO;
import perobobbot.lang.MessageDispatcher;
import perobobbot.lang.MessageHandler;
import perococco.command.PeroCommandControllerBuilder;

public interface CommandController extends MessageHandler {

    static @NonNull CommandControllerBuilder builder(@NonNull IO io,
                                                     @NonNull MessageDispatcher messageDispatcher,
                                                     @NonNull CommandExecutor commandExecutor) {
        return new PeroCommandControllerBuilder(io,messageDispatcher,commandExecutor);
    }

    void start();

    void stop();
}

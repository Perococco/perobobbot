package perobobbot.command;

import lombok.NonNull;
import perobobbot.lang.MessageDispatcher;
import perobobbot.lang.MessageListener;
import perobobbot.lang.Platform;
import perococco.command.PeroCommandControllerBuilder;

public interface CommandController extends MessageListener {

    static @NonNull CommandControllerBuilder builder(@NonNull MessageDispatcher messageDispatcher,
                                                     @NonNull CommandExecutor commandExecutor) {
        return new PeroCommandControllerBuilder(messageDispatcher,commandExecutor);
    }

    char getPrefix(@NonNull Platform platform);

    void start();

    void stop();
}

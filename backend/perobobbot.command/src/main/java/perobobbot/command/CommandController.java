package perobobbot.command;

import lombok.NonNull;
import perobobbot.chat.core.IO;
import perobobbot.lang.Bot;
import perobobbot.lang.MessageHandler;
import perobobbot.lang.Subscription;
import perobobbot.lang.fp.Function1;
import perococco.command.PeroCommandControllerBuilder;

public interface CommandController extends MessageHandler {

    static @NonNull CommandControllerBuilder builder(@NonNull IO io,
                                                     @NonNull Function1<? super CommandController,? extends Subscription> connector) {
        return new PeroCommandControllerBuilder(io,connector);
    }

    void start();

    void stop();

    @NonNull Subscription addCommandRegistry(@NonNull Bot bot, @NonNull ROCommandRegistry commandRegistry);
}

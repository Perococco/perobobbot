package perobobbot.common.messaging;

import lombok.NonNull;
import perobobbot.common.lang.ExecutionContext;
import perobobbot.common.lang.fp.Consumer1;
import perococco.perobobbot.common.messaging.ComplexCommand;
import perococco.perobobbot.common.messaging.SimpleCommand;

public interface Command {

    @NonNull
    static Command simple(@NonNull String name, @NonNull Consumer1<? super ExecutionContext> action) {
        return new SimpleCommand(name, action);
    }

    @NonNull
    static Command complex(@NonNull String name, @NonNull Command... commands) {
        return ComplexCommand.create(name, commands);
    }

    @NonNull
    static Command complex(@NonNull String name, @NonNull Consumer1<? super ExecutionContext> fallback, @NonNull Command... commands) {
        return ComplexCommand.create(name, fallback, commands);
    }

    @NonNull
    String name();

    void execute(@NonNull ExecutionContext context);

}

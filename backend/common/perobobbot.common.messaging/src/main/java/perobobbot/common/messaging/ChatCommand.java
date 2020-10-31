package perobobbot.common.messaging;

import lombok.NonNull;
import perobobbot.common.lang.ExecutionContext;
import perobobbot.common.lang.fp.Consumer1;
import perococco.perobobbot.common.messaging.ComplexChatCommand;
import perococco.perobobbot.common.messaging.SimpleChatCommand;

public interface ChatCommand {

    @NonNull
    static ChatCommand simple(@NonNull String name, @NonNull Consumer1<? super ExecutionContext> action) {
        return new SimpleChatCommand(name,action);
    }

    @NonNull
    static ChatCommand complex(@NonNull String name, @NonNull ChatCommand... commands) {
        return ComplexChatCommand.create(name,commands);
    }

    @NonNull
    static ChatCommand complex(@NonNull String name, @NonNull Consumer1<? super ExecutionContext> fallback, @NonNull ChatCommand... commands) {
        return ComplexChatCommand.create(name, fallback,commands);
    }

    @NonNull
    String name();

    void execute(@NonNull ExecutionContext context);

}

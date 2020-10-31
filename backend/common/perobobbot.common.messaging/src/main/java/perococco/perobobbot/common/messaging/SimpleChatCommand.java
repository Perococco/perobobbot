package perococco.perobobbot.common.messaging;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.lang.ExecutionContext;
import perobobbot.common.lang.fp.Consumer1;
import perobobbot.common.messaging.ChatCommand;

@RequiredArgsConstructor
public class SimpleChatCommand implements ChatCommand {

    @NonNull
    private final String name;

    @NonNull
    private final Consumer1<? super ExecutionContext> executor;

    @Override
    public void execute(@NonNull ExecutionContext context) {
        executor.f(context);
    }

    @Override
    public @NonNull String name() {
        return name;
    }
}

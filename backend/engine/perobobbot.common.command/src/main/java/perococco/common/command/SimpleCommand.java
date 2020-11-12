package perococco.common.command;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.command.Command;
import perobobbot.lang.ExecutionContext;
import perobobbot.lang.fp.Consumer1;

@RequiredArgsConstructor
public class SimpleCommand implements Command {

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

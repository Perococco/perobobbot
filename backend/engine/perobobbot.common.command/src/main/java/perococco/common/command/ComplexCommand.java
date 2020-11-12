package perococco.common.command;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.command.Command;
import perobobbot.lang.ExecutionContext;
import perobobbot.lang.MapTool;
import perobobbot.lang.fp.Consumer1;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
public class ComplexCommand implements Command {

    public static ComplexCommand create(@NonNull String name, @NonNull Command... subCommands) {
        return create(name, ctx -> {
        }, subCommands);
    }

    public static ComplexCommand create(@NonNull String name, @NonNull Consumer1<? super ExecutionContext> fallback, @NonNull Command... subCommands) {
        return new ComplexCommand(
                new SimpleCommand(name, fallback),
                Arrays.stream(subCommands).collect(MapTool.collector(Command::name))
        );
    }

    private final Command fallback;

    @NonNull
    private final ImmutableMap<String, Command> subCommands;

    @Override
    public @NonNull String name() {
        return fallback.name();
    }

    @Override
    public void execute(@NonNull ExecutionContext context) {
        final Runnable fallback = () -> this.fallback.execute(context);
        final ExecutionContext subContext = context.withSubCommands().orElse(null);

        Optional.ofNullable(subContext)
                .map(ExecutionContext::getCommandName)
                .map(subCommands::get)
                .ifPresentOrElse(
                        c -> c.execute(subContext),
                        fallback
                );

    }
}

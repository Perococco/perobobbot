package perococco.perobobbot.common.messaging;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.lang.ExecutionContext;
import perobobbot.common.lang.MapTool;
import perobobbot.common.lang.fp.Consumer1;
import perobobbot.common.messaging.ChatCommand;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
public class ComplexChatCommand implements ChatCommand {

    public static ComplexChatCommand create(@NonNull String name, @NonNull ChatCommand... subCommands) {
        return create(name, ctx -> {
        }, subCommands);
    }

    public static ComplexChatCommand create(@NonNull String name, @NonNull Consumer1<? super ExecutionContext> fallback, @NonNull ChatCommand... subCommands) {
        return new ComplexChatCommand(
                ChatCommand.simple(name, fallback),
                Arrays.stream(subCommands).collect(MapTool.collector(ChatCommand::name))
        );
    }

    private final ChatCommand fallback;

    @NonNull
    private final ImmutableMap<String, ChatCommand> subCommands;

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

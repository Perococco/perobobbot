package perococco.perobobbot.program.sample.echo.command;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.program.core.ChatCommand;
import perobobbot.program.core.ExecutionContext;
import perobobbot.program.core.ExecutionPolicy;
import perococco.perobobbot.program.sample.echo.EchoAction;

import java.time.Duration;

@RequiredArgsConstructor
public class EchoCommand implements ChatCommand {

    @Getter
    private final ExecutionPolicy executionPolicy = ExecutionPolicy.withGlobalCooldown(Duration.ofSeconds(10));

    @NonNull
    @Getter
    private final String name;

    @NonNull
    private final EchoAction echoAction;

    @Override
    public void execute(@NonNull ExecutionContext executionContext) {
        echoAction.performEchoTo(executionContext.getChannelInfo(), executionContext.getMessageOwner(), executionContext.getParameters());
    }


}

package perococco.perobobbot.program.sample.ping.command;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.program.core.ChatCommand;
import perobobbot.program.core.ExecutionContext;
import perobobbot.program.core.ExecutionPolicy;
import perococco.perobobbot.program.sample.ping.PingAction;

import java.time.Duration;

@RequiredArgsConstructor
public class PingCommand implements ChatCommand {

    @Getter
    private final @NonNull String name;

    @NonNull
    private final PingAction program;

    @NonNull
    @Getter
    private final ExecutionPolicy executionPolicy = ExecutionPolicy.withGlobalCooldown(Duration.ofSeconds(10));

    @Override
    public void execute(@NonNull ExecutionContext executionContext) {
        program.ping(executionContext.getChannelInfo(),executionContext.getReceptionTime());
    }
}

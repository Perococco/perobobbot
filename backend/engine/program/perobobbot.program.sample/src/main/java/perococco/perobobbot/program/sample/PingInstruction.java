package perococco.perobobbot.program.sample;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.chat.advanced.DispatchContext;
import perobobbot.common.lang.Nil;
import perobobbot.common.lang.Role;
import perobobbot.program.core.ExecutionContext;
import perobobbot.program.core.ExecutionPolicy;
import perobobbot.program.core.Instruction;

import java.time.Duration;
import java.time.Instant;

public class PingInstruction implements Instruction {

    @Override
    public @NonNull String getName() {
        return "myping";
    }

    @NonNull
    @Getter
    private final ExecutionPolicy executionPolicy = ExecutionPolicy.withGlobalCooldown(Duration.ofSeconds(10));

    public PingInstruction(Nil nil) {
    }

    @Override
    public void execute(@NonNull ExecutionContext executionContext, @NonNull String parameters) {
        executionContext.print(d -> pongMessage(d,executionContext.getReceptionTime()));
    }

    @NonNull
    private String pongMessage(@NonNull DispatchContext dispatchContext, @NonNull Instant receptionTime) {
        final Duration duration = Duration.between(receptionTime,dispatchContext.getDispatchingTime());
        return "mypong ("+duration.toMillis()+")";
    }

}

package perococco.perobobbot.program.sample;

import perobobbot.chat.advanced.DispatchContext;
import perobobbot.common.lang.Nil;
import perobobbot.common.lang.UserRole;
import perobobbot.program.core.ExecutionContext;
import perobobbot.program.core.ExecutionPolicy;
import perobobbot.program.core.Instruction;
import lombok.NonNull;

import java.time.Duration;
import java.time.Instant;

public class PingInstruction implements Instruction {

    @Override
    public @NonNull String getName() {
        return "myping";
    }

    public PingInstruction(Nil nil) {
    }

    @Override
    public boolean execute(@NonNull ExecutionContext executionContext, @NonNull String parameters) {
        executionContext.print(d -> pongMessage(d,executionContext.getReceptionTime()));
        return true;
    }

    private String pongMessage(@NonNull DispatchContext dispatchContext, @NonNull Instant receptionTime) {
        final Duration duration = Duration.between(receptionTime,dispatchContext.getDispatchingTime());
        return "mypong ("+duration.toMillis()+")";
    }


    @Override
    public @NonNull ExecutionPolicy getExecutionPolicy() {
        return new ExecutionPolicy(UserRole.ANY_USER,Duration.ofSeconds(10),Duration.ZERO);
    }
}

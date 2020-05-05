package perococco.bot.program.sample;

import bot.chat.advanced.DispatchContext;
import bot.common.lang.Nil;
import bot.common.lang.UserRole;
import bot.program.core.ExecutionContext;
import bot.program.core.ExecutionPolicy;
import bot.program.core.Instruction;
import lombok.NonNull;

import java.time.Duration;
import java.time.Instant;

public class PingInstruction implements Instruction {

    @Override
    public @NonNull String name() {
        return "myping";
    }

    public PingInstruction(Nil nil) {
    }

    @Override
    public boolean execute(@NonNull ExecutionContext executionContext, @NonNull String parameters) {
        executionContext.print(d -> pongMessage(d,executionContext.receptionTime()));
        return true;
    }

    private String pongMessage(@NonNull DispatchContext dispatchContext, @NonNull Instant receptionTime) {
        final Duration duration = Duration.between(receptionTime,dispatchContext.dispatchingTime());
        return "mypong ("+duration.toMillis()+")";
    }


    @Override
    public @NonNull ExecutionPolicy executionPolicy() {
        return new ExecutionPolicy(UserRole.ANY_USER,Duration.ofSeconds(10),Duration.ZERO);
    }
}

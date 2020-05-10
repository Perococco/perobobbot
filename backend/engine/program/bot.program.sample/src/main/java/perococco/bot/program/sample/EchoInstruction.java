package perococco.bot.program.sample;

import bot.common.lang.Nil;
import bot.common.lang.UserRole;
import bot.program.core.ExecutionContext;
import bot.program.core.ExecutionPolicy;
import bot.program.core.Instruction;
import lombok.Getter;
import lombok.NonNull;

import java.time.Duration;

public class EchoInstruction implements Instruction {

    @Getter
    private final ExecutionPolicy executionPolicy = new ExecutionPolicy(UserRole.ANY_USER, Duration.ofSeconds(10), Duration.ZERO);

    public EchoInstruction(Nil nil) {}

    @Override
    public @NonNull String getName() {
        return "echo";
    }

    @Override
    public boolean execute(@NonNull ExecutionContext executionContext, @NonNull String parameters) {
        executionContext.print(executionContext.getExecutingUser().getUserName() + " said " + parameters);
        return true;
    }
}

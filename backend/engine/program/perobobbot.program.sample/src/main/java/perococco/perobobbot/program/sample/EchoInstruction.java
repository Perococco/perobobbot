package perococco.perobobbot.program.sample;

import perobobbot.common.lang.Nil;
import perobobbot.common.lang.UserRole;
import perobobbot.program.core.ExecutionContext;
import perobobbot.program.core.ExecutionPolicy;
import perobobbot.program.core.Instruction;
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

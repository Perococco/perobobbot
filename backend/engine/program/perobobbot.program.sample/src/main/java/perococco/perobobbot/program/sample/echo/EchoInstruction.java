package perococco.perobobbot.program.sample.echo;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.common.lang.Nil;
import perobobbot.program.core.ExecutionContext;
import perobobbot.program.core.ExecutionPolicy;
import perobobbot.program.core.Instruction;

import java.time.Duration;

public class EchoInstruction implements Instruction {

    @Getter
    private final ExecutionPolicy executionPolicy = ExecutionPolicy.withGlobalCooldown(Duration.ofSeconds(10));

    public EchoInstruction(Nil nil) {}

    @Override
    public @NonNull String getName() {
        return "echo";
    }

    @Override
    public void execute(@NonNull ExecutionContext executionContext, @NonNull String parameters) {
        executionContext.print(executionContext.getExecutingUser().getUserName() + " said " + parameters);
    }
}

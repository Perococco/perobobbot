package perococco.perobobbot.program.core;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import perobobbot.program.core.ChatCommand;
import perobobbot.program.core.Execution;
import perobobbot.program.core.ExecutionContext;
import perobobbot.program.core.ExecutionPolicy;

import java.time.Instant;

@Log4j2
public class ChatCommandWithPolicyHandling implements ChatCommand {

    @NonNull
    @Getter
    private final String name;

    @NonNull
    private final Execution execution;

    @NonNull
    @Getter
    private final ExecutionPolicy executionPolicy;

    @NonNull
    private final ExecutionInfo executionInfo;

    public ChatCommandWithPolicyHandling(@NonNull String name, @NonNull Execution execution, @NonNull ExecutionPolicy executionPolicy) {
        this.name = name;
        this.execution = execution;
        this.executionPolicy = executionPolicy;
        this.executionInfo = new ExecutionInfo(executionPolicy);
    }

    @Override
    public void execute(@NonNull ExecutionContext executionContext) {
        final Instant now = Instant.now();
        if (executionInfo.canExecute(executionContext.getMessageOwner(), now)) {
            execution.execute(executionContext);
        } else {
            LOG.info("Execution of {} denied for user '{}'", getName(), executionContext.getMessageOwner().getUserId());
        }
    }

    public void cleanup() {
        executionInfo.cleanup();
    }

    @Override
    public String toString() {
        return "ChatCommandWithPolicyHandling{" +
               name +
               '}';
    }
}

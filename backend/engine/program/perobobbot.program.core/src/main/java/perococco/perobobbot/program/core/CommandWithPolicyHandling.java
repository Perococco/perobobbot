package perococco.perobobbot.program.core;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import perobobbot.program.core.ChatCommand;
import perobobbot.program.core.ExecutionContext;
import perobobbot.program.core.ExecutionPolicy;

import java.time.Instant;

@Log4j2
public class CommandWithPolicyHandling implements ChatCommand {

    @NonNull
    private final ChatCommand delegate;

    @NonNull
    private final ExecutionInfo executionInfo;

    public CommandWithPolicyHandling(@NonNull ChatCommand delegate) {
        this.delegate = delegate;
        this.executionInfo = new ExecutionInfo(delegate.getExecutionPolicy());
    }

    @Override
    public void execute(@NonNull ExecutionContext executionContext) {
        final Instant now = Instant.now();
        if (executionInfo.canExecute(executionContext.getMessageOwner(), now)) {
            delegate.execute(executionContext);
        } else {
            LOG.info("Execution of {} denied for user '{}'", getName(), executionContext.getMessageOwner().getUserId());
        }
    }

    public void cleanup() {
        executionInfo.cleanup();
    }

    @Override
    public @NonNull String getName() {
        return delegate.getName();
    }


    @Override
    public @NonNull ExecutionPolicy getExecutionPolicy() {
        return delegate.getExecutionPolicy();
    }

    @Override
    public String toString() {
        return "ChatCommandWithPolicyHandling{" +
               delegate.getName() +
               '}';
    }
}

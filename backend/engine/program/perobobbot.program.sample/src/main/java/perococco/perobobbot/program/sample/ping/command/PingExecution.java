package perococco.perobobbot.program.sample.ping.command;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.program.core.Execution;
import perobobbot.program.core.ExecutionContext;
import perococco.perobobbot.program.sample.ping.PingAction;

@RequiredArgsConstructor
public class PingExecution implements Execution {

    @NonNull
    private final PingAction program;

    @Override
    public void execute(@NonNull ExecutionContext executionContext) {
        program.ping(executionContext.getChannelInfo(),executionContext.getReceptionTime());
    }
}

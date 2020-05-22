package perococco.perobobbot.program.core;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import perobobbot.common.lang.MapTool;
import perobobbot.program.core.ExecutionContext;
import perobobbot.program.core.ExecutionPolicy;
import perobobbot.program.core.Program;
import perobobbot.program.core.UnknownInstruction;

import java.time.Instant;

@Log4j2
public class ProgramWithPolicyHandling implements Program {

    @NonNull
    private final Program delegate;

    @NonNull
    private final ImmutableMap<String, ExecutionInfo> executionInfoByInstruction;

    public ProgramWithPolicyHandling(@NonNull Program delegate) {
        this.delegate = delegate;
        this.executionInfoByInstruction = delegate.getInstructionNames()
                                                  .stream()
                                                  .map(n -> new ExecutionInfo(n, delegate.getExecutionPolicy(n)))
                                                  .collect(MapTool.collector(ExecutionInfo::getInstructionName));
    }

    public void cleanup() {
        executionInfoByInstruction.values().forEach(ExecutionInfo::cleanup);
    }

    @Override
    public @NonNull String getName() {
        return delegate.getName();
    }

    @Override
    public @NonNull ImmutableSet<String> getInstructionNames() {
        return delegate.getInstructionNames();
    }

    @Override
    public @NonNull ExecutionPolicy getExecutionPolicy(@NonNull String instructionName) {
        return delegate.getExecutionPolicy(instructionName);
    }

    @Override
    public void execute(@NonNull ExecutionContext executionContext, @NonNull String instructionName, @NonNull String parameters) {
        final ExecutionInfo executionInfo = getExecutionInfo(instructionName);
        if (executionInfo.canExecute(executionContext.getExecutingUser(), Instant.now())) {
            delegate.execute(executionContext,instructionName,parameters);
        } else {
            LOG.info("Execution of {}:{} denied for user '{}'", getName(), instructionName, executionContext.getExecutingUser().getUserId());
        }
    }

    @NonNull
    private ExecutionInfo getExecutionInfo(@NonNull String instructionName) {
        final ExecutionInfo executionInfo = executionInfoByInstruction.get(instructionName);
        if (executionInfo == null) {
            throw new UnknownInstruction(getName(), instructionName);
        }
        return executionInfo;
    }

}

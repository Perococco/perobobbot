package perococco.bot.program.core;

import bot.common.lang.MapTool;
import bot.program.core.ExecutionContext;
import bot.program.core.ExecutionPolicy;
import bot.program.core.Program;
import bot.program.core.UnknownInstruction;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

import java.time.Instant;

@Log4j2
public class ProgramWithPolicyHandling implements Program {

    @NonNull
    private final Program delegate;

    @NonNull
    private final ImmutableMap<String, ExecutionInfo> executionInfoByInstruction;

    public ProgramWithPolicyHandling(@NonNull Program delegate) {
        this.delegate = delegate;
        this.executionInfoByInstruction = delegate.instructionNames()
                                                  .stream()
                                                  .map(n -> new ExecutionInfo(n, delegate.getExecutionPolicy(n)))
                                                  .collect(MapTool.collector(ExecutionInfo::instructionName));
    }

    public void cleanup() {
        executionInfoByInstruction.values().forEach(ExecutionInfo::cleanup);
    }

    @Override
    public @NonNull String name() {
        return delegate.name();
    }

    @Override
    public @NonNull ImmutableSet<String> instructionNames() {
        return delegate.instructionNames();
    }

    @Override
    public @NonNull ExecutionPolicy getExecutionPolicy(@NonNull String instructionName) {
        return delegate.getExecutionPolicy(instructionName);
    }

    @Override
    public boolean execute(@NonNull ExecutionContext executionContext, @NonNull String instructionName, @NonNull String parameters) {
        final ExecutionInfo executionInfo = getExecutionInfo(instructionName);
        if (executionInfo.canExecute(executionContext.executingUser(), Instant.now())) {
            return delegate.execute(executionContext,instructionName,parameters);
        } else {
            return false;
        }
    }

    @NonNull
    private ExecutionInfo getExecutionInfo(@NonNull String instructionName) {
        final ExecutionInfo executionInfo = executionInfoByInstruction.get(instructionName);
        if (executionInfo == null) {
            throw new UnknownInstruction(name(), instructionName);
        }
        return executionInfo;
    }

}

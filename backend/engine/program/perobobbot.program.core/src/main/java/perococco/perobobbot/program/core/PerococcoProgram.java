package perococco.perobobbot.program.core;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.program.core.*;

@RequiredArgsConstructor
public class PerococcoProgram implements Program {

    @NonNull
    @Getter
    private final String name;

    @NonNull
    private final ImmutableMap<String, Instruction> instructions;

    @NonNull
    private final BackgroundTask backgroundTask;

    @NonNull
    private final MessageHandler messageHandler;

    @Override
    public @NonNull ImmutableSet<String> getInstructionNames() {
        return instructions.keySet();
    }

    @Override
    public void execute(@NonNull ExecutionContext executionContext, @NonNull String instructionName, @NonNull String parameters) {
        getInstruction(instructionName).execute(executionContext,parameters);
    }

    @Override
    public @NonNull ExecutionPolicy getExecutionPolicy(@NonNull String instructionName) {
        return getInstruction(instructionName).getExecutionPolicy();
    }

    @NonNull
    private Instruction getInstruction(@NonNull String instructionName) {
        final Instruction instruction = instructions.get(instructionName);
        if (instruction == null) {
            throw new UnknownInstruction(this.getName(), instructionName);
        }
        return instruction;
    }

    @Override
    public @NonNull ExecutionContext handleMessage(@NonNull ExecutionContext executionContext) {
        return messageHandler.handleMessage(executionContext);
    }

    @Override
    public void start() {
        backgroundTask.start();
    }

    @Override
    public void stop() {
        backgroundTask.stop();
    }

    @Override
    public String toString() {
        return "PerococcoProgram{" + name  + '}';
    }
}

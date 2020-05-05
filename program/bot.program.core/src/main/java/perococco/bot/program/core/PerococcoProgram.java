package perococco.bot.program.core;

import bot.program.core.*;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PerococcoProgram implements Program {

    @NonNull
    @Getter
    private final String name;

    @NonNull
    private final ImmutableMap<String, Instruction> instructions;

    @Override
    public @NonNull ImmutableSet<String> instructionNames() {
        return instructions.keySet();
    }

    @Override
    public boolean execute(@NonNull ExecutionContext executionContext, @NonNull String instructionName, @NonNull String parameters) {
        return getInstruction(instructionName).execute(executionContext,parameters);
    }

    @Override
    public @NonNull ExecutionPolicy getExecutionPolicy(@NonNull String instructionName) {
        return getInstruction(instructionName).executionPolicy();
    }

    @NonNull
    private Instruction getInstruction(@NonNull String instructionName) {
        final Instruction instruction = instructions.get(instructionName);
        if (instruction == null) {
            throw new UnknownInstruction(name(), instructionName);
        }
        return instruction;
    }
}

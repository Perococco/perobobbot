package perococco.perobobbot.program.core;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.program.core.ExecutionContext;
import perobobbot.program.core.Program;

@RequiredArgsConstructor
public class ProgramExecutionInfo implements NamedExecution {

    @NonNull
    private final ExecutionContext executionContext;

    @NonNull
    private final InstructionExtraction instructionExtraction;

    @NonNull
    private final Program program;

    @Override
    public void launch() {
        program.execute(executionContext,instructionExtraction.getInstructionName(),instructionExtraction.getParameters());
    }

    @NonNull
    @Override
    public String getName() {
        return program.getName();
    }
}

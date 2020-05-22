package perococco.perobobbot.program.core;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.program.core.ExecutionContext;
import perobobbot.program.core.Program;

@RequiredArgsConstructor
public class ProgramExecutionInfo {

    @NonNull
    private final ExecutionContext executionContext;

    @NonNull
    private final InstructionExtraction instructionExtraction;

    @NonNull
    private final Program program;

    public void launch() {
        program.execute(executionContext,instructionExtraction.getInstructionName(),instructionExtraction.getParameters());
    }

    @NonNull
    public String getProgramName() {
        return program.getName();
    }
}

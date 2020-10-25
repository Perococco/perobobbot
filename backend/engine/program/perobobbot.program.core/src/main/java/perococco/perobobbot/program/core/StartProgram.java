package perococco.perobobbot.program.core;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.lang.ExecutionContext;
import perobobbot.common.lang.fp.Consumer1;
import perobobbot.program.core.ProgramManager;

@RequiredArgsConstructor
public class StartProgram implements Consumer1<ExecutionContext> {

    @NonNull
    private final ProgramManager programManager;

    @Override
    public void f(@NonNull ExecutionContext executionContext) {
        programManager.startProgram(executionContext.getParameters());
    }
}

package perococco.perobobbot.program.core.manager.command;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.program.core.Execution;
import perobobbot.program.core.ExecutionContext;
import perobobbot.program.core.ProgramAction;

@RequiredArgsConstructor
public class StopAllPrograms implements Execution {

    @NonNull
    private final ProgramAction programAction;

    @Override
    public void execute(@NonNull ExecutionContext executionContext) {
        programAction.stopAllPrograms();
    }
}

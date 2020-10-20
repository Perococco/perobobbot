package perococco.perobobbot.program.core.manager.command;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.program.core.Execution;
import perobobbot.program.core.ExecutionContext;
import perobobbot.program.core.ProgramAction;

@RequiredArgsConstructor
public class StartProgram implements Execution {

    @NonNull
    private final ProgramAction programAction;

    @Override
    public void execute(@NonNull ExecutionContext executionContext) {
        final String programName = executionContext.getParameters().trim();
        if (programAction.isKnownProgram(programName)) {
            programAction.startProgram(programName);
        } else {
            programAction.warnForUnknownProgram(executionContext.getChannelInfo(), programName);
        }
    }

}

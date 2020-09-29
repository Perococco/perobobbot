package perococco.perobobbot.program.core.manager.command;

import lombok.NonNull;
import perobobbot.program.core.ExecutionContext;
import perobobbot.program.core.ProgramAction;

public class StopProgram extends ManagerChatCommand {

    public StopProgram(@NonNull String name, @NonNull ProgramAction programAction) {
        super(name, programAction);
    }

    @Override
    public void execute(@NonNull ExecutionContext executionContext) {
        final String programName = executionContext.getParameters().trim();
        final ProgramAction programAction = this.getProgramAction();
        if (programAction.isKnownProgram(programName)) {
            programAction.stopProgram(programName);
        } else {
            programAction.warnForUnknownProgram(executionContext.getChannelInfo(), programName);
        }
    }

}

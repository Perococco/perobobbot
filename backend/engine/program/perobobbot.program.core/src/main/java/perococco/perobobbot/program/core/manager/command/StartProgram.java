package perococco.perobobbot.program.core.manager.command;

import lombok.NonNull;
import perobobbot.program.core.ExecutionContext;
import perobobbot.program.core.ProgramAction;

public class StartProgram extends ManagerChatCommand {


    public StartProgram(@NonNull String name, @NonNull ProgramAction programAction) {
        super(name, programAction);
    }

    @Override
    public void execute(@NonNull ExecutionContext executionContext) {
        final String programName = executionContext.getParameters().trim();
        final ProgramAction programAction = this.getProgramAction();
        if (programAction.isKnownProgram(programName)) {
            programAction.startProgram(programName);
        } else {
            programAction.warnForUnknownProgram(executionContext.getChannelInfo(), programName);
        }
    }

}

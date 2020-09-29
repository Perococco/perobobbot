package perococco.perobobbot.program.core.manager.command;

import lombok.NonNull;
import perobobbot.program.core.ExecutionContext;
import perobobbot.program.core.ProgramAction;

public class ListPrograms extends ManagerChatCommand {

    public ListPrograms(@NonNull String name, @NonNull ProgramAction programAction) {
        super(name, programAction);
    }

    @Override
    public void execute(@NonNull ExecutionContext executionContext) {
        final ProgramAction programAction = getProgramAction();
        programAction.displayAllProgram(executionContext.getChannelInfo());
    }
}

package perococco.perobobbot.program.core.manager.command;

import lombok.NonNull;
import perobobbot.program.core.ExecutionContext;
import perobobbot.program.core.ProgramAction;

public class StopAllPrograms extends ManagerChatCommand {

    public StopAllPrograms(@NonNull String name, @NonNull ProgramAction programAction) {
        super(name, programAction);
    }

    @Override
    public void execute(@NonNull ExecutionContext executionContext) {
        getProgramAction().stopAllPrograms();
    }
}

package perococco.perobobbot.program.core.manager.command;

import lombok.NonNull;
import perobobbot.program.core.ExecutionContext;
import perobobbot.program.core.ProgramAction;

public class StartAllPrograms extends ManagerChatCommand {

    public StartAllPrograms(@NonNull String name, @NonNull ProgramAction identity) {
        super(name,identity);
    }

    @Override
    public void execute(@NonNull ExecutionContext executionContext) {
        getProgramAction().startAllPrograms();
    }
}

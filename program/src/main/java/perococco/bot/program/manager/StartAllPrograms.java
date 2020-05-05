package perococco.bot.program.manager;

import bot.program.ExecutionContext;
import lombok.NonNull;
import perococco.bot.program.ManagerIdentity;
import perococco.bot.program.ManagerState;

public class StartAllPrograms extends ManagerInstruction {

    public StartAllPrograms(@NonNull ManagerIdentity identity) {
        super(identity, "startAll");
    }

    @Override
    public boolean execute(@NonNull ExecutionContext executionContext, @NonNull String parameters) {
        identity().mutate(ManagerState::startAll);
        return true;
    }
}

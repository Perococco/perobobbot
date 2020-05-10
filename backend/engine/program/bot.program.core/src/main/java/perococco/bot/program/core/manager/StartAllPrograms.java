package perococco.bot.program.core.manager;

import bot.program.core.ExecutionContext;
import lombok.NonNull;
import perococco.bot.program.core.ManagerIdentity;
import perococco.bot.program.core.ManagerState;

public class StartAllPrograms extends ManagerInstruction {

    public StartAllPrograms(@NonNull ManagerIdentity identity) {
        super(identity, "startAll");
    }

    @Override
    public boolean execute(@NonNull ExecutionContext executionContext, @NonNull String parameters) {
        getIdentity().mutate(ManagerState::startAll);
        return true;
    }
}

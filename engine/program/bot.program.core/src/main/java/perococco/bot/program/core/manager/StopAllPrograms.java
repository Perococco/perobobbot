package perococco.bot.program.core.manager;

import bot.program.core.ExecutionContext;
import lombok.NonNull;
import perococco.bot.program.core.ManagerIdentity;
import perococco.bot.program.core.ManagerState;

public class StopAllPrograms extends ManagerInstruction {

    public StopAllPrograms(@NonNull ManagerIdentity identity) {
        super(identity, "stopAll");
    }

    @Override
    public boolean execute(@NonNull ExecutionContext executionContext, @NonNull String parameters) {
        getIdentity().mutate(ManagerState::stopAll);
        return true;
    }
}

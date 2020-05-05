package perococco.bot.program.manager;

import bot.program.ExecutionContext;
import lombok.NonNull;
import perococco.bot.program.ManagerIdentity;
import perococco.bot.program.ManagerState;

public class StopAllPrograms extends ManagerInstruction {

    public StopAllPrograms(@NonNull ManagerIdentity identity) {
        super(identity, "stopAll");
    }

    @Override
    public boolean execute(@NonNull ExecutionContext executionContext, @NonNull String parameters) {
        identity().mutate(ManagerState::stopAll);
        return true;
    }
}

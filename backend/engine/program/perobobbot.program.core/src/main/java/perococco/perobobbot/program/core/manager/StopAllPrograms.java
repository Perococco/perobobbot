package perococco.perobobbot.program.core.manager;

import perobobbot.program.core.ExecutionContext;
import lombok.NonNull;
import perococco.perobobbot.program.core.ManagerIdentity;
import perococco.perobobbot.program.core.ManagerState;

public class StopAllPrograms extends ManagerInstruction {

    public StopAllPrograms(@NonNull ManagerIdentity identity) {
        super(identity, "stopAll");
    }

    @Override
    public void execute(@NonNull ExecutionContext executionContext, @NonNull String parameters) {
        getIdentity().mutate(ManagerState::stopAll);
    }
}

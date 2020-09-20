package perococco.perobobbot.program.core.manager;

import lombok.NonNull;
import perobobbot.program.core.ExecutionContext;
import perococco.perobobbot.program.core.ManagerIdentity;
import perococco.perobobbot.program.core.ManagerState;

public class StopAllPrograms extends ManagerInstruction {

    public StopAllPrograms(@NonNull ManagerIdentity identity) {
        super(identity, "stopAll");
    }

    @Override
    public void execute(@NonNull ExecutionContext executionContext, @NonNull String parameters) {
        mutate(ManagerState::stopAll);
    }
}

package perococco.perobobbot.program.core.manager;

import perobobbot.program.core.ExecutionContext;
import lombok.NonNull;
import perococco.perobobbot.program.core.ManagerIdentity;
import perococco.perobobbot.program.core.ManagerState;

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

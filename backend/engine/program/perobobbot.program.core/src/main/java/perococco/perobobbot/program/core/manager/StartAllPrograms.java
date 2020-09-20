package perococco.perobobbot.program.core.manager;

import lombok.NonNull;
import perobobbot.program.core.ExecutionContext;
import perococco.perobobbot.program.core.ManagerIdentity;
import perococco.perobobbot.program.core.ManagerState;

public class StartAllPrograms extends ManagerInstruction {

    public StartAllPrograms(@NonNull ManagerIdentity identity) {
        super(identity, "startAll");
    }

    @Override
    public void execute(@NonNull ExecutionContext executionContext, @NonNull String parameters) {
        mutate(ManagerState::startAll);
    }
}

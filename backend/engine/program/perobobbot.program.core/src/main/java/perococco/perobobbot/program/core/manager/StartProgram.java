package perococco.perobobbot.program.core.manager;

import perobobbot.program.core.ExecutionContext;
import lombok.NonNull;
import perococco.perobobbot.program.core.ManagerIdentity;

public class StartProgram extends ManagerInstruction {

    public static final String NAME = "start";

    public StartProgram(@NonNull ManagerIdentity identity) {
        super(identity, NAME);
    }

    @Override
    public void execute(@NonNull ExecutionContext executionContext, @NonNull String parameters) {
        if (!getIdentity().getState().isKnownProgram(parameters)) {
            warnForUnknownProgram(executionContext,parameters);
        } else {
            getIdentity().mutate(s -> s.enableProgram(parameters));
        }
    }

}

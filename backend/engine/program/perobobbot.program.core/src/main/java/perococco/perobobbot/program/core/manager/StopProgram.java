package perococco.perobobbot.program.core.manager;

import perobobbot.program.core.ExecutionContext;
import lombok.NonNull;
import perococco.perobobbot.program.core.ManagerIdentity;

public class StopProgram extends ManagerInstruction {

    public static final String NAME = "stop";

    public StopProgram(@NonNull ManagerIdentity identity) {
        super(identity, NAME);
    }

    @Override
    public boolean execute(@NonNull ExecutionContext executionContext, @NonNull String programName) {
        if (!getIdentity().getState().isKnownProgram(programName)) {
            warnForUnknownProgram(executionContext,programName);
        } else {
            getIdentity().mutate(s -> s.disableProgram(programName));
        }
        return true;
    }

}

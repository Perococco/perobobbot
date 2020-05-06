package perococco.bot.program.core.manager;

import bot.program.core.ExecutionContext;
import lombok.NonNull;
import perococco.bot.program.core.ManagerIdentity;

public class StopProgram extends ManagerInstruction {

    public static final String NAME = "stop";

    public StopProgram(@NonNull ManagerIdentity identity) {
        super(identity, NAME);
    }

    @Override
    public boolean execute(@NonNull ExecutionContext executionContext, @NonNull String programName) {
        if (!identity().getState().isKnownProgram(programName)) {
            warnForUnknownProgram(executionContext,programName);
        } else {
            identity().mutate(s -> s.disableProgram(programName));
        }
        return true;
    }

}

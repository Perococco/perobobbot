package perococco.bot.program.manager;

import bot.program.ExecutionContext;
import lombok.NonNull;
import perococco.bot.program.ManagerIdentity;

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

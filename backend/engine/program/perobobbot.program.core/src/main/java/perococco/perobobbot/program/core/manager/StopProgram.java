package perococco.perobobbot.program.core.manager;

import lombok.NonNull;
import perobobbot.program.core.ExecutionContext;
import perococco.perobobbot.program.core.ManagerIdentity;
import perococco.perobobbot.program.core.manager.mutation.StopProgramMutation;

public class StopProgram extends ManagerInstruction {

    public static final String NAME = "stop";

    public StopProgram(@NonNull ManagerIdentity identity) {
        super(identity, NAME);
    }

    @Override
    public void execute(@NonNull ExecutionContext executionContext, @NonNull String programName) {
        if (!getState().isKnownProgram(programName)) {
            warnForUnknownProgram(executionContext,programName);
        } else {
            mutate(new StopProgramMutation(programName));
        }
    }

}

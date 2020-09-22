package perococco.perobobbot.program.core.manager;

import lombok.NonNull;
import perobobbot.program.core.ExecutionContext;
import perococco.perobobbot.program.core.ManagerIdentity;
import perococco.perobobbot.program.core.manager.mutation.StartAllProgramMutation;
import perococco.perobobbot.program.core.manager.mutation.StartProgramMutation;

public class StartProgram extends ManagerInstruction {

    public static final String NAME = "start";

    public StartProgram(@NonNull ManagerIdentity identity) {
        super(identity, NAME);
    }

    @Override
    public void execute(@NonNull ExecutionContext executionContext, @NonNull String parameters) {
        if (!getState().isKnownProgram(parameters)) {
            warnForUnknownProgram(executionContext,parameters);
        } else {
            mutate(new StartProgramMutation(parameters));
        }
    }

}

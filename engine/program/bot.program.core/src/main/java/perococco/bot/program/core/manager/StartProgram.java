package perococco.bot.program.core.manager;

import bot.program.core.ExecutionContext;
import lombok.NonNull;
import perococco.bot.program.core.ManagerIdentity;

public class StartProgram extends ManagerInstruction {

    public static final String NAME = "start";

    public StartProgram(@NonNull ManagerIdentity identity) {
        super(identity, NAME);
    }

    @Override
    public boolean execute(@NonNull ExecutionContext executionContext, @NonNull String parameters) {
        if (!identity().getState().isKnownProgram(parameters)) {
            warnForUnknownProgram(executionContext,parameters);
        } else {
            identity().mutate(s -> s.enableProgram(parameters));
        }
        return true;
    }

}

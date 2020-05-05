package perococco.bot.program.core.manager;

import bot.program.core.ExecutionContext;
import lombok.NonNull;
import perococco.bot.program.core.ManagerIdentity;

import java.util.stream.Collectors;

public class ListPrograms extends ManagerInstruction {

    public ListPrograms(@NonNull ManagerIdentity identity) {
        super(identity, "list");
    }

    @Override
    public boolean execute(@NonNull ExecutionContext executionContext, @NonNull String parameters) {
        final String message = identity().getState().programNames().stream()
                .collect(Collectors.joining(", ","Available programs: ","."));
        executionContext.print(message);
        return true;
    }
}

package perococco.perobobbot.program.core.manager;

import perobobbot.program.core.ExecutionContext;
import lombok.NonNull;
import perococco.perobobbot.program.core.ManagerIdentity;

import java.util.stream.Collectors;

public class ListPrograms extends ManagerInstruction {

    public ListPrograms(@NonNull ManagerIdentity identity) {
        super(identity, "list");
    }

    @Override
    public boolean execute(@NonNull ExecutionContext executionContext, @NonNull String parameters) {
        final String message = getIdentity().getState().programNames().stream()
                .collect(Collectors.joining(", ","Available programs: ","."));
        executionContext.print(message);
        return true;
    }
}
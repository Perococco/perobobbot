package perococco.perobobbot.program.core.manager;

import lombok.NonNull;
import perobobbot.program.core.ExecutionContext;
import perococco.perobobbot.program.core.ManagerIdentity;

import java.util.stream.Collectors;

public class ListPrograms extends ManagerInstruction {

    public ListPrograms(@NonNull ManagerIdentity identity) {
        super(identity, "list");
    }

    @Override
    public void execute(@NonNull ExecutionContext executionContext, @NonNull String parameters) {
        final String message = getIdentity().getState().programNames().stream()
                .collect(Collectors.joining(", ","Available programs: ","."));
        executionContext.print(message);
    }
}

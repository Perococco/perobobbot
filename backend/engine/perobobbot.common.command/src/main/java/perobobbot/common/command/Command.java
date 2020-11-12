package perobobbot.common.command;

import lombok.NonNull;
import perobobbot.lang.ExecutionContext;
import perococco.common.command.PeroCommandFactory;

/**
 * A command that launch an execution
 */
public interface Command {

    @NonNull
    String name();

    void execute(@NonNull ExecutionContext context);

    static CommandFactory factory() { return new PeroCommandFactory();}

}

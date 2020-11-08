package perobobbot.common.command;

import lombok.NonNull;
import perobobbot.common.lang.ExecutionContext;
import perococco.common.command.PeroCommandFactory;

public interface Command {

    @NonNull
    String name();

    void execute(@NonNull ExecutionContext context);

    static CommandFactory factory() { return new PeroCommandFactory();}

}

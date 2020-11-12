package perobobbot.command;

import lombok.NonNull;
import perobobbot.lang.ExecutionContext;

/**
 * A command that launch an execution
 */
public interface Command {

    /**
     * @return the name of the command. This is used to identify a command
     * from a chat message
     */
    @NonNull
    String name();

    /**
     * Execute this command
     * @param context the context in which the command is executed (like the user, the time of execution, the source
     *                of the execution)
     */
    void execute(@NonNull ExecutionContext context);

}

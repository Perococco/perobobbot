package perobobbot.program.core;

import lombok.NonNull;

public interface ChatCommand {


    interface Factory<P> {
        @NonNull
        ChatCommand create(@NonNull String name, @NonNull P parameter);
    }

    /**
     * @return the name of the command
     */
    @NonNull
    String getName();

    /**
     * @param executionContext the context this command is executing in
     */
    void execute(@NonNull ExecutionContext executionContext);

    /**
     * @return the execution policy of this instruction (who can execute this instruction, how often...)
     */
    @NonNull
    ExecutionPolicy getExecutionPolicy();

}

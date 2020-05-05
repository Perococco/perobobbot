package bot.program;

import lombok.NonNull;

public interface Instruction {

    /**
     * @return the name of the instruction
     */
    @NonNull
    String name();

    /**
     * @param executionContext the context the instruction is executing in
     * @param parameters the part of the message after the instruction name
     */
    boolean execute(@NonNull ExecutionContext executionContext, @NonNull String parameters);

    @NonNull
    ExecutionPolicy executionPolicy();
}

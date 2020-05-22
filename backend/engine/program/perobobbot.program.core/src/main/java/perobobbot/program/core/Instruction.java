package perobobbot.program.core;

import lombok.NonNull;

public interface Instruction {

    /**
     * @return the name of the instruction
     */
    @NonNull
    String getName();

    /**
     * @param executionContext the context the instruction is executing in
     * @param parameters the part of the message after the instruction name
     */
    void execute(@NonNull ExecutionContext executionContext, @NonNull String parameters);

    @NonNull
    ExecutionPolicy getExecutionPolicy();
}

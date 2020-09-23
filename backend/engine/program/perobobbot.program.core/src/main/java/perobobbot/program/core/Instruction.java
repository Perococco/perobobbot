package perobobbot.program.core;

import lombok.NonNull;

/**
 * A single instruction.
 */
public interface Instruction {

    interface Factory<S> {
        @NonNull
        Instruction create(@NonNull S programState);
    }

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

    /**
     * @return the execution policy of this instruction (who can executed this instruction, how often...)
     */
    @NonNull
    ExecutionPolicy getExecutionPolicy();
}

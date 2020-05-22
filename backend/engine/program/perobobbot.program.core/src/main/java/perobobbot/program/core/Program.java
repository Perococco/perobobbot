package perobobbot.program.core;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perococco.perobobbot.program.core.PerococcoProgramBuilder;

public interface Program {

    /**
     * @return the name of the program
     */
    @NonNull
    String getName();

    /**
     * @return the set of all instructions of this program
     */
    @NonNull
    ImmutableSet<String> getInstructionNames();

    /**
     * @param name a name of an instruction
     * @return true if this program has an instruction with the provided name, false otherwise
     */
    default boolean hasInstruction(@NonNull String name) {
        return getInstructionNames().contains(name);
    }

    /**
     * @param instructionName the name of an instruction
     * @return the {@link ExecutionPolicy} of the instruction with the given name
     * @throws UnknownInstruction if no instruction with the provided name exists for this program
     */
    @NonNull
    ExecutionPolicy getExecutionPolicy(@NonNull String instructionName);

    /**
     * @param executionContext the execution context containing information like the user executing the action
     * @param instructionName the name of the instruction to execute
     * @param parameters the parameters of the instruction
     * @return true if the execution should be stop, i.e. no other program should run after this one
     */
    void execute(@NonNull ExecutionContext executionContext, @NonNull String instructionName, @NonNull String parameters);

    @NonNull
    static <S> ProgramBuilder<S> create(@NonNull S state) {
        return new PerococcoProgramBuilder<>(state);
    }


}

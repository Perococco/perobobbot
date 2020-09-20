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
     */
    void execute(@NonNull ExecutionContext executionContext, @NonNull String instructionName, @NonNull String parameters);

    /**
     * Create a builder of a program
     * @param programState the state of the program. Pass to {@link Instruction.Factory#create(Object)}
     *                      when added to this builder
     * @param <I> the type of the state of the program
     * @return a program builder
     */
    @NonNull
    static <I> ProgramBuilder<I> builder(@NonNull I programState) {
        return new PerococcoProgramBuilder<>(programState);
    }


}

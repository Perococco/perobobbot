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
     * start the program.
     * launch the background task associated with it
     */
    void start();

    /**
     * stop the program.
     * stop the background task associated with it
     */
    void stop();

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
     * @param instructionName  the name of the instruction to execute
     * @param parameters       the parameters of the instruction
     */
    void execute(@NonNull ExecutionContext executionContext, @NonNull String instructionName, @NonNull String parameters);

    /**
     * Called only if the execution context has not been used by any instruction of any registered programs
     *
     * @param executionContext the current execution context
     * @return the execution context to pass to this method of the next program.
     */
    @NonNull
    ExecutionContext handleMessage(@NonNull ExecutionContext executionContext);

    /**
     * Create a builder of a program
     *
     * @param programState the state of the program. Passed to {@link Instruction.Factory#create(Object)}
     *                     and {@link BackgroundTask.Factory#create(Object)}
     *                     when added to this builder
     * @param <I>          the type of the state of the program
     * @return a program builder
     */
    @NonNull
    static <I> ProgramBuilder<I> builder(@NonNull I programState) {
        return new PerococcoProgramBuilder<>(programState);
    }


}

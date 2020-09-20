package perobobbot.program.core;

import lombok.NonNull;

public interface ProgramBuilder<S> {

    /**
     * @param name the value to use for the name of the program
     * @return this
     */
    @NonNull
    ProgramBuilder<S> name(@NonNull String name);

    /**
     * Create and add an instruction to the builder
     * @param factory the factory used to create the instruction. The argument passed
     *                to the method {@link perobobbot.program.core.Instruction.Factory#create(Object)}
     *                is the value used when creating this builder.
     * @return this
     */
    @NonNull
    ProgramBuilder<S> addInstruction(@NonNull Instruction.Factory<? super S> factory);

    @NonNull
    ProgramBuilder<S> setMessageHandler(@NonNull MessageHandler.Factory<? super S> factory);

    /**
     * built the program and return it
     * @return the built program
     */
    @NonNull
    Program build();
}

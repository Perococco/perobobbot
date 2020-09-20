package perococco.perobobbot.program.core;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.program.core.Instruction;
import perobobbot.program.core.Program;
import perobobbot.program.core.ProgramBuilder;

@RequiredArgsConstructor
public class PerococcoProgramBuilder<S> implements ProgramBuilder<S> {

    /**
     * the state of the program, used
     */
    @NonNull
    private final S state;

    private String name = null;

    private final ImmutableMap.Builder<String,Instruction> instructionBuilder = ImmutableMap.builder();

    @Override
    public @NonNull ProgramBuilder<S> name(@NonNull String name) {
        this.name = name;
        return this;
    }

    @Override
    public @NonNull ProgramBuilder<S> addInstruction(@NonNull Instruction.Factory<? super S> factory) {
        final Instruction instruction = factory.create(state);
        this.instructionBuilder.put(instruction.getName(), instruction);
        return this;
    }

    @Override
    public @NonNull Program build() {
        return new PerococcoProgram(name,instructionBuilder.build());
    }
}

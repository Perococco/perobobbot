package perococco.bot.program.core;

import bot.common.lang.fp.Function1;
import bot.program.core.Instruction;
import bot.program.core.Program;
import bot.program.core.ProgramBuilder;
import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PerococcoProgramBuilder<S> implements ProgramBuilder<S> {

    @NonNull
    private final S state;

    private String name = null;

    private ImmutableMap.Builder<String,Instruction> instructionBuilder = ImmutableMap.builder();

    @Override
    public @NonNull ProgramBuilder<S> name(@NonNull String name) {
        this.name = name;
        return this;
    }

    @Override
    public @NonNull ProgramBuilder<S> addInstruction(@NonNull Function1<? super S, ? extends Instruction> instructionFactory) {
        final Instruction instruction = instructionFactory.apply(state);
        this.instructionBuilder.put(instruction.name(),instruction);
        return this;
    }

    @Override
    public @NonNull Program build() {
        return new PerococcoProgram(name,instructionBuilder.build());
    }
}

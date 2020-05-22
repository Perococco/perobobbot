package perobobbot.program.core;

import lombok.NonNull;
import perobobbot.common.lang.fp.Function1;

public interface ProgramBuilder<S> {

    @NonNull
    ProgramBuilder<S> name(@NonNull String name);

    @NonNull
    ProgramBuilder<S> addInstruction(@NonNull Function1<? super S, ? extends Instruction> instructionFactory);

    @NonNull
    Program build();
}

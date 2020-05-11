package perobobbot.program.core;

import perobobbot.common.lang.fp.Function1;
import lombok.NonNull;

public interface ProgramBuilder<S> {

    @NonNull
    ProgramBuilder<S> name(@NonNull String name);

    @NonNull
    ProgramBuilder<S> addInstruction(@NonNull Function1<? super S, ? extends Instruction> instructionFactory);

    @NonNull
    Program build();
}

package perobobbot.program.core;

import lombok.NonNull;
import perococco.perobobbot.program.core.PerococcoProgramExecutor;

public interface ProgramExecutor {

    @NonNull
    static ProgramExecutor create() {
        return new PerococcoProgramExecutor("@","!");
    }

    void registerProgram(@NonNull Program program);

    boolean handleMessage(@NonNull ExecutionContext executionContext);

}

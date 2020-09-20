package perobobbot.program.core;

import lombok.NonNull;
import perococco.perobobbot.program.core.PerococcoProgramExecutor;

public interface ProgramExecutor {

    @NonNull
    static ProgramExecutor create(@NonNull String prefixForManager, @NonNull String prefixForPrograms) {
        return new PerococcoProgramExecutor(prefixForManager,prefixForPrograms);
    }

    @NonNull
    static ProgramExecutor create() {
        return new PerococcoProgramExecutor("@","!");
    }

    /**
     * Register a program to this executor
     * @param program the program to register
     */
    void registerProgram(@NonNull Program program);

    /**
     * call this program executor. The provided context
     * is used to determine the right program to call
     * as well as the parameters to pass to this program
     */
    void handleMessage(@NonNull ExecutionContext executionContext);

}

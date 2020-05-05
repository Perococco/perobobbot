package bot.program;

import lombok.NonNull;
import perococco.bot.program.PerococcoProgramExecutor;

public interface ProgramExecutor {

    @NonNull
    static ProgramExecutor create() {
        return new PerococcoProgramExecutor("@","!");
    }

    void registerProgram(@NonNull Program program);

    boolean handleMessage(@NonNull ExecutionContext executionContext);

}

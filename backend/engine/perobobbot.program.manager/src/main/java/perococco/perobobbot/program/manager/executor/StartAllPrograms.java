package perococco.perobobbot.program.manager.executor;

import lombok.NonNull;
import perobobbot.common.lang.ExecutionContext;
import perobobbot.program.core.ProgramExecutor;
import perobobbot.program.manager.ProgramManager;

public class StartAllPrograms extends ProgramExecutor<ProgramManager> {

    public StartAllPrograms(@NonNull ProgramManager program) {
        super(program);
    }

    @Override
    protected void doExecute(ProgramManager program, ExecutionContext context) {
        program.startAll();
    }
}

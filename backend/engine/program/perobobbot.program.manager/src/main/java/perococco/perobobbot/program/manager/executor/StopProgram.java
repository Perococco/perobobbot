package perococco.perobobbot.program.manager.executor;

import lombok.NonNull;
import perobobbot.common.lang.ExecutionContext;
import perobobbot.program.core.ProgramExecutor;
import perobobbot.program.manager.ProgramManager;

public class StopProgram extends ProgramExecutor<ProgramManager> {

    public StopProgram(@NonNull ProgramManager program) {
        super(program);
    }

    @Override
    protected void doExecute(ProgramManager program, ExecutionContext context) {
        program.stopProgram(context.getParameters());
    }
}

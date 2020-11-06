package perococco.perobobbot.program.manager.executor;

import lombok.NonNull;
import perobobbot.common.lang.ExecutionContext;
import perobobbot.program.core.ProgramExecutor;
import perobobbot.program.manager.ProgramManager;

public class StartProgram extends ProgramExecutor<ProgramManager> {

    public StartProgram(@NonNull ProgramManager program) {
        super(program);
    }

    @Override
    protected void doExecute(ProgramManager program, ExecutionContext context) {
        program.startProgram(context.getParameters());
    }

}
